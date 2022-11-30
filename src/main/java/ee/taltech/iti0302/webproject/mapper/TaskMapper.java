package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.task.PaginatedTaskDto;
import ee.taltech.iti0302.webproject.dto.task.CreateTaskDto;
import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.dto.task.TaskDto;
import ee.taltech.iti0302.webproject.dto.task.UpdateTaskDto;
import ee.taltech.iti0302.webproject.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task toEntity(CreateTaskDto dto);

    @Mapping(source = "task.project.id", target = "projectId")
    @Mapping(source = "task.status.id", target = "statusId")
    @Mapping(source = "task.milestone.id", target = "milestoneId")
    TaskDto toDto(Task task, List<LabelDto> labels);

    List<TaskDto> toDtoList(List<Task> tasks);
    PaginatedTaskDto toPaginatedDto(Integer totalPages, Integer page, Integer size, List<TaskDto> tasks);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTaskFromDto(UpdateTaskDto dto, @MappingTarget Task task);
}
