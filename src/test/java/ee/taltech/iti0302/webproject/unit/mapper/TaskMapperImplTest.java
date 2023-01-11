package ee.taltech.iti0302.webproject.unit.mapper;

import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Label;
import ee.taltech.iti0302.webproject.entity.Milestone;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.entity.Status;
import ee.taltech.iti0302.webproject.entity.Task;
import ee.taltech.iti0302.webproject.mapper.TaskMapper;
import ee.taltech.iti0302.webproject.mapper.TaskMapperImpl;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskMapperImplTest {
    private TaskMapper taskMapper = new TaskMapperImpl();

    @Test
    void toDto_AllTaskVariables_ReturnsTaskDto() {
        Task task = Task.builder()
                .id(1)
                .status(Status.builder().id(2).build())
                .milestone(Milestone.builder().id(1).build())
                .assignee(AppUser.builder().id(1).build())
                .labels(List.of(Label.builder().id(11).build()))
                .deadline(LocalDate.now().plusDays(1))
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description")
                .build();
        LabelDto labelDto = LabelDto.builder().id(1).build();
        var result = taskMapper.toDto(task, List.of(labelDto));

        assertEquals(11, result.getLabels().get(0).getId());
        assertEquals(2, result.getStatusId());
    }

    @Test
    void toDtoList_ValidData_ReturnsListOfTaskDtos() {
        Task task = Task.builder()
                .id(1)
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description")
                .build();
        Task task2 = Task.builder()
                .id(2)
                .title("task title")
                .project(Project.builder().id(1).build())
                .description("description2")
                .build();
        var result = taskMapper.toDtoList(List.of(task, task2));

        assertEquals("description", result.get(0).getDescription());
        assertEquals("description2", result.get(1).getDescription());
    }
}