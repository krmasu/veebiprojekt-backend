package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.UpdateTaskDto;
import ee.taltech.iti0302.webproject.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;



@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    void createTask_CorrectDtoGiven_ReturnsPaginatedDto() {
        // Given
        CreateTaskDto createTaskDto = new CreateTaskDto();
        Pageable pageable = Pageable.unpaged();
        PaginatedTaskDto paginatedTaskDto = new PaginatedTaskDto();
        given(taskService.createTask(createTaskDto, pageable)).willReturn(paginatedTaskDto);

        // When
        Principal principal = () -> "name";
        var result  = taskController.createTask(1, createTaskDto, principal, Pageable.unpaged());

        // Then
        then(taskService).should().createTask(createTaskDto, pageable);
        assertEquals(paginatedTaskDto, result);
    }

    @Test
    void getTask_ByProjectId_ReturnsPaginatedDto() {
        // Given
        PaginatedTaskDto paginatedTaskDto = new PaginatedTaskDto();
        Pageable pageable = Pageable.unpaged();
        given(taskService.getTasks(1, pageable, null, null, null, null)).willReturn(paginatedTaskDto);

        // When
        Principal principal = () -> "name";
        var result  = taskController.getTasks(1, null, null, null, null, principal, pageable);

        // Then
        then(taskService).should().getTasks(1, pageable, null, null, null, null);
        assertEquals(paginatedTaskDto, result);
    }

    @Test
    void deleteTask_CorrectDataAsInput_ReturnsPaginatedDto() {
        // Given
        Pageable pageable = Pageable.unpaged();
        PaginatedTaskDto paginatedTaskDto = new PaginatedTaskDto();
        given(taskService.deleteTask(1, 1, pageable)).willReturn(paginatedTaskDto);

        // When
        var result  = taskController.deleteTask(1, 1, pageable);

        // Then
        then(taskService).should().deleteTask(1, 1, pageable);
        assertEquals(paginatedTaskDto, result);
    }

    @Test
    void updateTask_CorrectDataAsInput_ReturnsPaginatedDto() {
        // Given
        UpdateTaskDto updateTaskDto = new UpdateTaskDto();
        Pageable pageable = Pageable.unpaged();
        PaginatedTaskDto paginatedTaskDto = new PaginatedTaskDto();
        given(taskService.updateTask(1, updateTaskDto, pageable, 1)).willReturn(paginatedTaskDto);

        // When
        var result  = taskController.updateTask(1, 1, updateTaskDto, pageable);

        // Then
        then(taskService).should().updateTask(1, updateTaskDto, pageable, 1);
        assertEquals(paginatedTaskDto, result);
    }
}
