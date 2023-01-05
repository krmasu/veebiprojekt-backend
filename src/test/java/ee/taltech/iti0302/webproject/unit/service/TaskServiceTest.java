package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.task.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.entity.*;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.TaskMapper;
import ee.taltech.iti0302.webproject.repository.*;
import ee.taltech.iti0302.webproject.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    private static final Pageable PAGEABLE_SIZE_5 = Pageable.ofSize(5);
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ProjectNotFound_ThrowResourceNotFoundException() {
        // Given
        Task task = Task.builder().id(1).title("task").description("description").build();
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .statusId(1)
                .projectId(1)
                .build();
        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(createTaskDto, PAGEABLE_SIZE_5));

        // Then
        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
    }
    @Test
    void createTask_UserNotFound_ThrowResourceNotFoundException() {
        // Given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .statusId(1)
                .projectId(1)
                .assigneeId(1)
                .build();
        Task task = Task.builder().id(1).title("task").description("description").build();
        Project project = Project.builder().tasks(List.of(task)).build();
        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(userRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(createTaskDto, PAGEABLE_SIZE_5));

        // Then
        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
        then(userRepository).should().findById(1);
    }

    @Test
    void createTask_StatusNotFound_ThrowResourceNotFoundException() {
        // Given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .statusId(1)
                .projectId(1)
                .assigneeId(1)
                .build();
        Task task = Task.builder().id(1).title("task").description("description").build();
        Project project = Project.builder().tasks(List.of(task)).build();
        AppUser appUser = AppUser.builder().id(1).build();
        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(userRepository.findById(1)).willReturn(Optional.of(appUser));
        given(statusRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(createTaskDto, PAGEABLE_SIZE_5));

        // Then
        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
        then(userRepository).should().findById(1);
        then(statusRepository).should().findById(1);
    }

    @Test
    void createTask_MilestoneNotFound_ThrowResourceNotFoundException() {
        // Given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .statusId(1)
                .assigneeId(1)
                .milestoneId(1)
                .build();
        Task task = Task.builder().id(1).title("task").description("description").build();
        Project project = Project.builder().tasks(List.of(task)).build();
        AppUser appUser = AppUser.builder().id(1).build();
        Status status = Status.builder().id(1).build();

        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(userRepository.findById(1)).willReturn(Optional.of(appUser));
        given(statusRepository.findById(1)).willReturn(Optional.of(status));
        given(milestoneRepository.findById(1)).willReturn(Optional.empty());

        // When
        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(createTaskDto, PAGEABLE_SIZE_5));

        // Then
        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
        then(userRepository).should().findById(1);
        then(statusRepository).should().findById(1);
        then(milestoneRepository).should().findById(1);
    }

    @Test
    void createTask_OnlyProjectId_OtherRepositoriesNoInteraction() {
        // Given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .build();
        Task task = Task.builder().id(1).title("task").description("description").build();
        Project project = Project.builder().id(1).tasks(List.of(task)).build();
        Page<Task> page = Page.empty(PAGEABLE_SIZE_5);
        Status status = Status.builder().id(1).build();
        PaginatedTaskDto paginatedTaskDto = PaginatedTaskDto.builder()
                .totalPages(page.getTotalPages())
                .page(PAGEABLE_SIZE_5.getPageNumber())
                .size(PAGEABLE_SIZE_5.getPageSize())
                .tasks(new ArrayList<>())
                .build();

        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(taskRepository.findAllByProjectId(1, PAGEABLE_SIZE_5)).willReturn(page);
        given(statusRepository.findById(1)).willReturn(Optional.of(status));
        given(taskMapper
                .toPaginatedDto(page.getTotalPages(),
                        PAGEABLE_SIZE_5.getPageNumber(),
                        PAGEABLE_SIZE_5.getPageSize(),
                        new ArrayList<>()))
                .willReturn(paginatedTaskDto);

        // When
        PaginatedTaskDto result = taskService.createTask(createTaskDto, PAGEABLE_SIZE_5);

        // Then
        assertEquals(paginatedTaskDto, result);

        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
        then(statusRepository).should().findById(1);
        then(taskRepository).should().save(task);
        then(taskRepository).should().findAllByProjectId(1, PAGEABLE_SIZE_5);

        then(userRepository).shouldHaveNoInteractions();
        then(milestoneRepository).shouldHaveNoInteractions();
    }

    @Test
    void createTask_EveryInputIsPresent_AllRepositoriesInteraction() {
        // Given
        CreateTaskDto createTaskDto = CreateTaskDto.builder()
                .projectId(1)
                .title("task")
                .description("description")
                .statusId(1)
                .assigneeId(1)
                .milestoneId(1)
                .labelIds(List.of(1))
                .build();
        Task task = Task.builder().id(1).title("task").description("description").build();
        Project project = Project.builder().id(1).tasks(List.of(task)).build();
        AppUser appUser = AppUser.builder().id(1).build();
        Status status = Status.builder().id(1).build();
        Milestone milestone = Milestone.builder().id(1).build();
        Label label = Label.builder().id(1).build();
        Page<Task> page = Page.empty(PAGEABLE_SIZE_5);
        PaginatedTaskDto paginatedTaskDto = PaginatedTaskDto.builder()
                .totalPages(page.getTotalPages())
                .page(PAGEABLE_SIZE_5.getPageNumber())
                .size(PAGEABLE_SIZE_5.getPageSize())
                .tasks(new ArrayList<>())
                .build();

        given(taskMapper.toEntity(createTaskDto)).willReturn(task);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(userRepository.findById(1)).willReturn(Optional.of(appUser));
        given(statusRepository.findById(1)).willReturn(Optional.of(status));
        given(milestoneRepository.findById(1)).willReturn(Optional.of(milestone));
        given(labelRepository.findAllById(List.of(1))).willReturn(List.of(label));
        given(taskRepository.findAllByProjectId(1, PAGEABLE_SIZE_5)).willReturn(page);
        given(statusRepository.findById(1)).willReturn(Optional.of(status));
        given(taskMapper
                .toPaginatedDto(page.getTotalPages(),
                        PAGEABLE_SIZE_5.getPageNumber(),
                        PAGEABLE_SIZE_5.getPageSize(),
                        new ArrayList<>()))
                .willReturn(paginatedTaskDto);

        // When
        PaginatedTaskDto result = taskService.createTask(createTaskDto, PAGEABLE_SIZE_5);

        // Then
        assertEquals(paginatedTaskDto, result);
        then(taskMapper).should().toEntity(createTaskDto);
        then(projectRepository).should().findById(1);
        then(userRepository).should().findById(1);
        then(statusRepository).should().findById(1);
        then(milestoneRepository).should().findById(1);
        then(labelRepository).should().findAllById(List.of(1));
        then(taskRepository).should().save(task);
        then(taskRepository).should().findAllByProjectId(1, PAGEABLE_SIZE_5);
    }

    @Test
    void deleteTask_EveryInputIsPresent_CheckInteractionsAndOutput() {
        // Given
        Page<Task> page = Page.empty(PAGEABLE_SIZE_5);
        PaginatedTaskDto paginatedTaskDto = PaginatedTaskDto.builder()
                .totalPages(page.getTotalPages())
                .page(PAGEABLE_SIZE_5.getPageNumber())
                .size(PAGEABLE_SIZE_5.getPageSize())
                .tasks(new ArrayList<>())
                .build();

        given(taskRepository.findAllByProjectId(1, PAGEABLE_SIZE_5)).willReturn(page);
        given(taskMapper
                .toPaginatedDto(page.getTotalPages(),
                        PAGEABLE_SIZE_5.getPageNumber(),
                        PAGEABLE_SIZE_5.getPageSize(),
                        new ArrayList<>()))
                .willReturn(paginatedTaskDto);

        // When
        PaginatedTaskDto result = taskService.deleteTask(1, 1, PAGEABLE_SIZE_5);

        // Then
        assertEquals(paginatedTaskDto, result);
        then(taskRepository).should().deleteById(1);
        then(taskRepository).should().findAllByProjectId(1, PAGEABLE_SIZE_5);
        then(taskMapper).should().toPaginatedDto(page.getTotalPages(),
                PAGEABLE_SIZE_5.getPageNumber(),
                PAGEABLE_SIZE_5.getPageSize(),
                new ArrayList<>());
    }
}
