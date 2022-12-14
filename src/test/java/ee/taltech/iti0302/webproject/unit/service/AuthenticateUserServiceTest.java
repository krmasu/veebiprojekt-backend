package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.ProjectMapperImpl;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapperImpl;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    }
}