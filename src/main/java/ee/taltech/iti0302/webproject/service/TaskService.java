package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.task.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.TaskDto;
import ee.taltech.iti0302.webproject.dto.task.UpdateTaskDto;
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
    public PaginatedTaskDto createTask(CreateTaskDto dto, Pageable pageable) {
        Task task = taskMapper.toEntity(dto);

        Project project = projectRepository.findById(dto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        task.setProject(project);

        Integer assigneeId = dto.getAssigneeId();
        if (assigneeId != null) {
            AppUser assignee = userRepository.findById(assigneeId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setAssignee(assignee);
        }

        Integer statusId = dto.getStatusId();
        Status status = statusRepository.findById(Objects.requireNonNullElse(statusId, 1)).orElseThrow(() -> new ResourceNotFoundException("Status not found"));
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

        Page<Task> tasks = taskRepository.findAllByProjectId(project.getId(), pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), tasks.getNumber(), tasks.getSize(), tasksToTaskDtos(tasks.getContent()));
    }

    public PaginatedTaskDto getTasks(Integer projectId, Pageable pageable, String title, String assigneeName, Integer statusId, Integer milestoneId) {
        Specification<Task> specification = Specification
                .where(TaskSpecification.byProject(projectId))
                .and(title == null ? null : TaskSpecification.titleContains(title))
                .and(assigneeName == null ? null : TaskSpecification.assigneeContains(assigneeName))
                .and(statusId == null ? null : TaskSpecification.byStatus(statusId))
                .and(milestoneId == null ? null : TaskSpecification.byMilestone(milestoneId));
        Page<Task> tasks = taskRepository.findAll(specification, pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), tasks.getNumber(), tasks.getSize(), tasksToTaskDtos(tasks.getContent()));
    }

    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task : tasks) {
            TaskDto dto = taskMapper.toDto(task, labelMapper.toDtoList(task.getLabels()));
            dtos.add(dto);
        }
        return dtos;
    }

    public PaginatedTaskDto deleteTask(Integer projectId, Integer taskId, Pageable pageable) {
        taskRepository.deleteById(taskId);
        Page<Task> tasks = taskRepository.findAllByProjectId(projectId, pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), tasks.getNumber(), tasks.getSize(), tasksToTaskDtos(tasks.getContent()));
    }

    public PaginatedTaskDto updateTask(Integer projectId, UpdateTaskDto dto, Pageable pageable, Integer taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task to update not found"));
        taskMapper.updateTaskFromDto(dto, task);
        Integer assigneeId = dto.getAssigneeId();
        if (assigneeId != null) {
            task.setAssignee(userRepository.findById(dto.getAssigneeId()).orElseThrow(() -> new ResourceNotFoundException("User not found")));
        }
        Integer statusId = dto.getStatusId();
        if (statusId != null) {
            task.setStatus(statusRepository.findById(dto.getStatusId()).orElseThrow(() -> new ResourceNotFoundException("Status not found")));
        }
        List<Integer> labelIds = dto.getLabelIds();
        if (labelIds != null && !labelIds.isEmpty()) {
            task.setLabels(labelRepository.findAllById(dto.getLabelIds()));
        }
        Integer milestoneId = dto.getMilestoneId();
        if (milestoneId != null) {
            task.setMilestone(milestoneRepository.findById(dto.getMilestoneId()).orElseThrow(() -> new ResourceNotFoundException("Milestone not found")));
        }
        Page<Task> tasks = taskRepository.findAllByProjectId(projectId, pageable);
        return taskMapper.toPaginatedDto(tasks.getTotalPages(), tasks.getNumber(), tasks.getSize(), tasksToTaskDtos(tasks.getContent()));
    }
}
