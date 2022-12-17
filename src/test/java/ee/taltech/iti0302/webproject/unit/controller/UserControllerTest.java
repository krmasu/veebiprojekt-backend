package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.UserController;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.user.UserRequestDto;
import ee.taltech.iti0302.webproject.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        assertEquals(1, result.getProjects().size());
        assertEquals("user", result.getUsername());
    }

}