package ee.taltech.iti0302.webproject.service.mapper;

import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    LoginUserDto toDto(AppUser user, List<ProjectDto> projects);
}
