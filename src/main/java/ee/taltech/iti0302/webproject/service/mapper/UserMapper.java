package ee.taltech.iti0302.webproject.service.mapper;

import ee.taltech.iti0302.webproject.dto.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.RegisterUserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    LoginUserDto toLoginUserDto(AppUser user, List<ProjectDto> projects);
    LoginResponseDto toLoginResponseDto(String authToken, String email, List<ProjectDto> projects);
    AppUser toEntity(RegisterUserDto userDto);
}
