package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.task.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.UpdateTaskDto;
import ee.taltech.iti0302.webproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("api/project/{projectId}/task")
    public PaginatedTaskDto createTask(@PathVariable("projectId") Integer projectId, @RequestBody CreateTaskDto dto, Principal principal, Pageable pageable) {
        log.info("Creating task for project with id: {} for user with id: {}", projectId, principal.getName());
        return taskService.createTask(dto, pageable);
    }

    @GetMapping("api/project/{projectId}/task")
    public PaginatedTaskDto getTasks(@PathVariable("projectId") Integer projectId,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "assignee", required = false) String assigneeName,
                                     @RequestParam(name = "status", required = false) Integer statusId,
                                     @RequestParam(name = "milestone", required = false) Integer milestone,
                                     Principal principal, Pageable pageable) {
        log.info("Getting tasks from project with id: {} for user with id: {}", projectId, principal.getName());
        return taskService.getTasks(projectId, pageable, title, assigneeName, statusId, milestone);
    }

    @DeleteMapping("api/project/{projectId}/{taskId}")
    public PaginatedTaskDto deleteTask(@PathVariable("projectId") Integer projectId, @PathVariable("taskId") Integer taskId, Pageable pageable) {
        log.info("Deleting task with id: {} from project with id: {}", taskId, projectId);
        return taskService.deleteTask(projectId, taskId, pageable);
    }

    @PatchMapping("api/project/{projectId}/{taskId}")
    public PaginatedTaskDto updateTask(@PathVariable("projectId") Integer projectId,
                                       @RequestBody UpdateTaskDto dto,
                                       Pageable pageable,
                                       @PathVariable("taskId") Integer taskId) {

        log.info("Updating task with id: {} in project with id: {}", taskId, projectId);
        return taskService.updateTask(projectId, dto, pageable, taskId);
    }
}
