package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.UserController;
import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.PaginatedUserResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    @Test
    void GetUserData_ValidId_ReturnsUserDto() {
        // given
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .id(1)
                .build();
        UserDto userDto = UserDto.builder()
                .id(1)
                .email("email@email.com")
                .username("user")
                .projects(List.of(ProjectDto.builder()
                        .id(1)
                        .title("Project")
                        .build()))
                .build();

        given(userService.getUserData(userRequestDto)).willReturn(userDto);
        // when
        var result = userController.getUserData(userRequestDto);
        // then
        then(userService).should().getUserData(userRequestDto);
        given(userService.getUserData(1)).willReturn(userDto);

        var result = userController.getUserData(1);

        assertEquals(1, result.getProjects().size());
        assertEquals("user", result.getUsername());
    }

    @Test
    void GetProjectMembers_MultipleMembers_ReturnsListOfUserResponseDtos() {
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        UserResponseDto userResponseDto1 = UserResponseDto.builder()
                .id(1)
                .username("aadu1")
                .build();
        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .id(2)
                .username("aadu2")
                .build();
        UserResponseDto userResponseDto3 = UserResponseDto.builder()
                .id(3)
                .username("aadu3")
                .build();
        userResponseDtos.add(userResponseDto1);
        userResponseDtos.add(userResponseDto2);
        userResponseDtos.add(userResponseDto3);

        Pageable pageable = Pageable.unpaged();
        Principal principal = () -> "name";

        PaginatedUserResponseDto paginatedUserResponseDto = PaginatedUserResponseDto.builder()
                .totalPages(1)
                .page(0)
                .size(3)
                .users(userResponseDtos)
                .build();

        given(userService.getProjectMembers(1, pageable)).willReturn(paginatedUserResponseDto);

        var result = userController.getProjectMembers(1, principal, pageable);

        assertEquals(3, result.getUsers().size());
        assertTrue(result.getUsers().contains(userResponseDto3));
    }

    @Test
    void AddNewProjectMembers_AddMultiple_ReturnsListOfUserResponseDto() {
        AddMembersToProjectDto addMembersToProjectDto = AddMembersToProjectDto.builder()
                .userIds(List.of(2, 3))
                .build();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        UserResponseDto userResponseDto1 = UserResponseDto.builder()
                .id(1)
                .username("aadu1")
                .build();
        UserResponseDto userResponseDto2 = UserResponseDto.builder()
                .id(2)
                .username("aadu2")
                .build();
        UserResponseDto userResponseDto3 = UserResponseDto.builder()
                .id(3)
                .username("aadu3")
                .build();
        userResponseDtos.add(userResponseDto1);
        userResponseDtos.add(userResponseDto2);
        userResponseDtos.add(userResponseDto3);

        Pageable pageable = Pageable.unpaged();

        PaginatedUserResponseDto paginatedUserResponseDto = PaginatedUserResponseDto.builder()
                .totalPages(1)
                .page(0)
                .size(3)
                .users(userResponseDtos)
                .build();

        given(userService.addNewProjectMembers(1, addMembersToProjectDto, pageable)).willReturn(paginatedUserResponseDto);

        var result = userController.addNewProjectMembers(1, addMembersToProjectDto, pageable);

        assertEquals(3, result.getUsers().size());
    }
}