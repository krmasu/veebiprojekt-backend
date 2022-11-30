package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.TaskDto;
import ee.taltech.iti0302.webproject.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    @PostMapping("api/project/{projectId}/task")
    public List<TaskDto> createTask(@PathVariable("projectId") Integer projectId, @RequestBody CreateTaskDto dto, Principal principal) {
        log.info("Creating task for project with id: {} user with id: {}", projectId, principal.getName());
        return taskService.createTask(dto);
    }

    @GetMapping("api/project/{projectId}/task")
    public PaginatedTaskDto getTasks(@PathVariable("projectId") Integer projectId,
                                     @RequestParam(name = "title", required = false) String title,
                                     @RequestParam(name = "assignee", required = false) String assigneeName,
                                     @RequestParam(name = "status", required = false) String statusId,
                                     @RequestParam(name = "milestone", required = false) String milestone,
                                     Principal principal, Pageable pageable) {
        log.info("Getting tasks from project with id: {} for user with id: {}", projectId, principal.getName());
        return taskService.getTasks(projectId, pageable, title, assigneeName, statusId, milestone);
    }

}
