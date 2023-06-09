package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.project.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.project.UpdateProjectDto;
import ee.taltech.iti0302.webproject.entity.Project;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    Project toEntity(CreateProjectDto createProjectDto);
    ProjectDto toDto(Project project);
    List<ProjectDto> toDtoList(List<Project> projects);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProjectFromDto(UpdateProjectDto dto, @MappingTarget Project entity);
}
