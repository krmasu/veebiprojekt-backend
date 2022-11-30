package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.TaskDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Label;
import ee.taltech.iti0302.webproject.entity.Milestone;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.entity.Status;
import ee.taltech.iti0302.webproject.entity.Task;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.LabelMapper;
import ee.taltech.iti0302.webproject.mapper.TaskMapper;
import ee.taltech.iti0302.webproject.repository.LabelRepository;
import ee.taltech.iti0302.webproject.repository.MilestoneRepository;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.repository.StatusRepository;
import ee.taltech.iti0302.webproject.repository.TaskRepository;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final MilestoneRepository milestoneRepository;
    private final StatusRepository statusRepository;
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;
    public List<TaskDto> createTask(CreateTaskDto dto) {
        Task task = taskMapper.toEntity(dto);

        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        task.setProject(project);

        Integer assigneeId = dto.getAssigneeId();
        if (assigneeId != null) {
            AppUser assignee = userRepository.findById(assigneeId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setAssignee(assignee);
        }

        Integer statusId = dto.getStatusId();
        Status status;
        status = statusRepository.findById(Objects.requireNonNullElse(statusId, 1)).orElseThrow(() -> new ResourceNotFoundException("Status not found"));
        task.setStatus(status);

        Integer milestoneId = dto.getMilestoneId();
        if (milestoneId != null) {
            Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new ResourceNotFoundException("Milestone not found"));
            task.setMilestone(milestone);
        }

        List<Integer> labelIds = dto.getLabelIds();
        if (labelIds != null && !labelIds.isEmpty()) {
            List<Label> labels = labelRepository.findAllById(labelIds);
            task.setLabels(labels);
        }

        taskRepository.save(task);

        List<Task> tasks = project.getTasks();
        return tasksToTaskDtos(tasks);
    }

    public PaginatedTaskDto getTasks(Integer projectId, Pageable pageable, String title, String assigneeName, Integer statusId, Integer milestoneId) {
        Specification<Task> specification = Specification
                .where(TaskSpecification.byProject(projectId))
                .and(title == null ? null : TaskSpecification.titleContains(title))
                .and(assigneeName == null ? null : TaskSpecification.assigneeContains(assigneeName))
                .and(statusId == null ? null : TaskSpecification.byStatus(statusId))
                .and(milestoneId == null ? null : TaskSpecification.byMilestone(milestoneId));
        Page<Task> tasks = taskRepository.findAll(specification, pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), pageable.getPageNumber(), pageable.getPageSize(), tasksToTaskDtos(tasks.getContent()));
    }

    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task1 : tasks) {
            TaskDto dto1 = taskMapper.toDto(task1, labelMapper.toDtoList(task1.getLabels()));
            dtos.add(dto1);
        }
        return dtos;
    }

    public PaginatedTaskDto deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId);
        Pageable pageable = Pageable.ofSize(10);
        Page<Task> tasks = taskRepository.findAll(pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), 0, 10, tasksToTaskDtos(tasks.getContent()));
    }
}
