package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.label.UpdateLabelDto;
import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.dto.label.PaginatedLabelDto;
import ee.taltech.iti0302.webproject.entity.Label;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabelMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Label toEntity(UpdateLabelDto dto);
    LabelDto toDto(Label label);

    List<LabelDto> toDtoList(List<Label> labels);

    PaginatedLabelDto toPaginatedDto(Integer totalPages, Integer page, Integer size, List<LabelDto> labels);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLabelFromDto(UpdateLabelDto dto, @MappingTarget Label label);
}
