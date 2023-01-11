package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.authentication.LoginRequestDto;
import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.InvalidCredentialsException;
import ee.taltech.iti0302.webproject.exception.UserExistsException;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.ProjectMapperImpl;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapperImpl;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserServiceTest {
    @InjectMocks
    private AuthenticateUserService authenticateUserService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private ProjectMapper projectMapper = new ProjectMapperImpl();

    @Spy
    private UserMapper userMapper = new UserMapperImpl();

    @Spy
    private PasswordEncoder passwordEncoder;


    @Test
    void registerUser_ValidData_ReturnsRegisterResponseDto() {
        // given
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .email("email")
                .password("password")
                .build();
        given(userMapper.toEntity(registerUserDto)).willReturn(user);
        user.setId(1);
        given(userRepository.save(user)).willReturn(user);
        // when
        var result = authenticateUserService.registerUser(registerUserDto);
        // then
        then(userRepository).should().existsByUsername("username");
        then(userRepository).should().existsByEmail("email");
        then(passwordEncoder).should().encode(registerUserDto.getPassword());
        then(userRepository).should().save(user);

        assertEquals("email", result.getEmail());
        assertTrue(result.isOk());
    }

    @Test
    void registerUser_UsernameExists_ThrowsUserExistsException() {
        // given
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        given(userRepository.existsByUsername("username")).willReturn(true);
        // when
        assertThrows(UserExistsException.class, () -> authenticateUserService.registerUser(registerUserDto));
        // then
        then(userRepository).should().existsByUsername("username");
        then(userRepository).should().existsByEmail("email");
    }

    @Test
    void registerUser_EmailExists_ThrowsUserExistsException() {
        // given
        RegisterUserDto registerUserDto = RegisterUserDto.builder()
                .username("username")
                .email("email")
                .password("password")
                .build();
        given(userRepository.existsByEmail("email")).willReturn(true);
        // when
        assertThrows(UserExistsException.class, () -> authenticateUserService.registerUser(registerUserDto));
        // then
        then(userRepository).should().existsByUsername("username");
        then(userRepository).should().existsByEmail("email");
    }

    @Test
    void loginUser_GivenValidCredentials_ReturnsLoginResponseDto() {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("username")
                .password("password")
                .build();

        Project project = Project.builder()
                .id(1)
                .title("project")
                .build();
        List<Project> projects = new ArrayList<>();
        projects.add(project);

        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .password("password")
                .projects(projects)
                .build();

        List<ProjectDto> projectDtos = new ArrayList<>();
        ProjectDto projectDto = ProjectDto.builder()
                .id(1)
                .title("project")
                .build();
        projectDtos.add(projectDto);

        given(userRepository.findByUsernameIgnoreCase("username")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password", "password")).willReturn(true);

        // when
        var result = authenticateUserService.loginUser(loginRequestDto);

        // then
        then(userRepository).should().findByUsernameIgnoreCase("username");
        then(passwordEncoder).should().matches("password", "password");
        then(projectMapper).should().toDtoList(user.getProjects());
        then(userMapper).should().toLoginResponseDto(result.getAuthToken(), user.getEmail(), projectDtos, user.getId());
        assertEquals(projectDtos, result.getProjects());
    }

    @Test
    void loginUser_InvalidUsername_ThrowsInvalidCredentialsException() {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("username")
                .password("password")
                .build();

        given(userRepository.findByUsernameIgnoreCase("username")).willReturn(Optional.empty());

        // when
        assertThrows(InvalidCredentialsException.class, () -> authenticateUserService.loginUser(loginRequestDto));

        // then
        then(userRepository).should().findByUsernameIgnoreCase("username");
    }
    @Test
    void loginUser_InvalidPassword_ThrowsInvalidCredentialsException() {
        // given
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username("username")
                .password("password")
                .build();

        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .password("password1")
                .build();

        given(userRepository.findByUsernameIgnoreCase("username")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password", "password1")).willReturn(false);
        // when
        assertThrows(InvalidCredentialsException.class, () -> authenticateUserService.loginUser(loginRequestDto));
        // then
        then(userRepository).should().findByUsernameIgnoreCase("username");
        then(passwordEncoder).should().matches("password", "password1");
    }
}