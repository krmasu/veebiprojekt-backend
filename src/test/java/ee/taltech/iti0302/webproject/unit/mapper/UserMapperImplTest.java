package ee.taltech.iti0302.webproject.unit.mapper;

import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.entity.Task;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperImplTest {
    private UserMapper userMapper = new UserMapperImpl();

    @Test
    void toLoginUserDto_AllUserVariables_ReturnUserDto() {
        Task task = Task.builder()
                .id(1)
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description")
                .build();

        Project project = Project.builder().id(1).build();
        ProjectDto projectDto = ProjectDto.builder()
                .id(3)
                .title("project")
                .build();

        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .email("user@user.com")
                .password("password")
                .assignedTasks(List.of(task))
                .projects(List.of(project))
                .build();

        var result = userMapper.toLoginUserDto(user, List.of(projectDto));

        assertEquals("username", result.getUsername());
        assertEquals(1, result.getId());
    }

    @Test
    void toLoginResponseDto_AllVariables_ReturnLoginResponseDto() {
        ProjectDto projectDto = ProjectDto.builder()
                .id(3)
                .title("project")
                .build();

        var result = userMapper
                .toLoginResponseDto("authToken", "email@email.com", List.of(projectDto), 1);

        assertEquals("authToken", result.getAuthToken());
        assertEquals("email@email.com", result.getEmail());
    }

    @Test
    void toResponseDto_AllVariables_ReturnUserResponseDto() {
        Task task = Task.builder()
                .id(1)
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description")
                .build();

        Project project = Project.builder().id(1).build();

        AppUser user = AppUser.builder()
                .id(1)
                .username("username")
                .email("user@user.com")
                .password("password")
                .assignedTasks(List.of(task))
                .projects(List.of(project))
                .build();

        var result = userMapper.toResponseDto(user);
        assertEquals(1, result.getId());
        assertEquals("username", result.getUsername());
    }

    @Test
    void toResponseDtoList_AllVariables_ReturnUserResponseDtoList() {
        Task task = Task.builder()
                .id(1)
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description")
                .build();

        Project project = Project.builder().id(1).build();

        AppUser user1 = AppUser.builder()
                .id(1)
                .username("username1")
                .email("user@user.com")
                .password("password")
                .assignedTasks(List.of(task))
                .projects(List.of(project))
                .build();

        AppUser user2 = AppUser.builder()
                .id(2)
                .username("username2")
                .email("user@user.com")
                .password("password")
                .assignedTasks(List.of(task))
                .projects(List.of(project))
                .build();

        var result = userMapper.toResponseDtoList(List.of(user1, user2));
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    void toPaginatedUserResponseDto_AllVariables_ReturnPaginatedUserResponseDto() {
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(1)
                .username("username")
                .build();
        var result = userMapper.toPaginatedUserResponseDto(1, 0, 1, List.of(userResponseDto));

        assertEquals(1, result.getTotalPages());
        assertEquals(0, result.getPage());
    }
}
