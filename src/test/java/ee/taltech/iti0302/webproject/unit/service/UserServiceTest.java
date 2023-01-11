package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.PaginatedUserResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.ProjectMapperImpl;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapperImpl;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.service.AuthenticateUserService;
import ee.taltech.iti0302.webproject.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectMapper projectMapper = new ProjectMapperImpl();

    @Mock
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void getUserData_UserDoesNotExists_ThrowsResourceNotFoundException() {
        // when
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserData(1));
    }

    @Test
    void getUserData_UserExists_ReturnUserData() {
        // given
        UserDto userDto = UserDto.builder().id(1).build();

        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .email("email")
                .password("password")
                .projects(List.of())
                .build();

        given(userRepository.findById(1)).willReturn(Optional.ofNullable(user));
        given(projectMapper.toDtoList(List.of())).willReturn(List.of());
        given(userMapper.toLoginUserDto(user, List.of())).willReturn(userDto);

        // when
        var result = userService.getUserData(1);

        // then
        then(userRepository).should().findById(1);
        then(projectMapper).should().toDtoList(List.of());
        then(userMapper).should().toLoginUserDto(user, List.of());

        assertEquals(1, result.getId());
    }

    @Test
    void getProjectMembers_ProjectDoesNotExists_ThrowsResourceNotFoundException() {
        // given

        Pageable pageable = Pageable.ofSize(5);

        // when

        assertThrows(ResourceNotFoundException.class, () -> userService.getProjectMembers(1, pageable));
    }

    @Test
    void getProjectMembers_ProjectExists_ReturnPaginatedUserResponseDto() {
        // given

        Pageable pageable = Pageable.ofSize(5);

        Project project = Project.builder().id(1).build();
        AppUser user = AppUser.builder()
                .id(2)
                .username("Mati")
                .projects(List.of(project))
                .build();

        Page<AppUser> page = new PageImpl<>(List.of(user));
        UserResponseDto userResponseDto = UserResponseDto.builder().id(2).username("Mati").build();

        PaginatedUserResponseDto paginatedUserResponseDto = PaginatedUserResponseDto.builder()
                .totalPages(1)
                .page(0)
                .size(1)
                .users(List.of(userResponseDto))
                .build();

        given(projectRepository.findById(1)).willReturn(Optional.ofNullable(project));
        given(userRepository.findAllByProjectsContains(project, pageable)).willReturn(page);
        given(userMapper.toResponseDtoList(page.getContent())).willReturn(List.of(userResponseDto));
        given(userMapper.toPaginatedUserResponseDto(1, 0, 1, List.of(userResponseDto)))
                .willReturn(paginatedUserResponseDto);

        // when
        var result = userService.getProjectMembers(1, pageable);

        // then
        then(projectRepository).should().findById(1);
        then(userRepository).should().findAllByProjectsContains(project, pageable);
        then(userMapper).should().toPaginatedUserResponseDto(1, 0, 1, List.of(userResponseDto));

        assertEquals(List.of(userResponseDto), result.getUsers());

    }

    @Test
    void addNewProjectMember_ProjectExists_ReturnPaginatedUserResponseDto() {
        // given
        Project userProject = Project.builder().id(3).users(List.of()).build();
        Pageable pageable = Pageable.ofSize(5);
        List<Project> userProjects = new ArrayList<>();
        userProjects.add(userProject);

        AppUser user = AppUser.builder()
                .id(2)
                .username("Mati")
                .projects(userProjects)
                .build();

        AddMembersToProjectDto addMembersToProjectDto = AddMembersToProjectDto.builder().userIds(List.of(2)).build();
        UserResponseDto userResponseDto = UserResponseDto.builder().id(2).username("Mati").build();

        Project project = Project.builder().id(1).users(new ArrayList<>()).build();

        Page<AppUser> page = new PageImpl<>(List.of(user));

        PaginatedUserResponseDto paginatedUserResponseDto = PaginatedUserResponseDto.builder()
                .totalPages(1)
                .page(0)
                .size(1)
                .users(List.of(userResponseDto))
                .build();


        given(projectRepository.findById(1)).willReturn(Optional.ofNullable(project));
        given(userRepository.findById(2)).willReturn(Optional.of(user));
        given(userRepository.findAllByProjectsContains(project, pageable)).willReturn(page);
        given(userMapper.toResponseDtoList(page.getContent())).willReturn(List.of(userResponseDto));
        given(userMapper.toPaginatedUserResponseDto(1, 0, 1, List.of(userResponseDto)))
                .willReturn(paginatedUserResponseDto);

        // when
        var result = userService.addNewProjectMembers(1,addMembersToProjectDto, pageable);

        // then

        then(projectRepository).should().findById(1);
        then(userRepository).should().findById(2);
        then(userRepository).should().findAllByProjectsContains(project, pageable);
        then(userMapper).should().toPaginatedUserResponseDto(1, 0, 1,List.of(userResponseDto));

        assertEquals(List.of(userResponseDto), result.getUsers());
    }
}
