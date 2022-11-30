package ee.taltech.iti0302.webproject.service;

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
import lombok.RequiredArgsConstructor;
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

    public List<TaskDto> getTasks(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        List<Task> tasks = project.getTasks();
        return tasksToTaskDtos(tasks);
    }

    public List<TaskDto> tasksToTaskDtos(List<Task> tasks) {
        List<TaskDto> dtos = new ArrayList<>();
        for (Task task1 : tasks) {
            TaskDto dto1 = taskMapper.toDto(task1, labelMapper.toDtoList(task1.getLabels()));
            dtos.add(dto1);
        }
        return dtos;
    }
}
