package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.entity.Label;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LabelMapper {

    LabelDto toDto(Label label);

    List<LabelDto> toDtoList(List<Label> labels);
}
