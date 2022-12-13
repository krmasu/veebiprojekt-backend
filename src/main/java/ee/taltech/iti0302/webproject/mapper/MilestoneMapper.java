package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.milestone.UpdateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.CreateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.MilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.PaginatedMilestoneDto;
import ee.taltech.iti0302.webproject.entity.Milestone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MilestoneMapper {
    MilestoneDto toDto(Milestone milestone);
    List<MilestoneDto> toDtoList(List<Milestone> milestones);

    PaginatedMilestoneDto toPaginatedDto(Integer totalPages, Integer page, Integer size, List<MilestoneDto> milestones);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Milestone toEntity(CreateMilestoneDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateMilestoneDto updateMilestoneDto, @MappingTarget Milestone milestone);

}
