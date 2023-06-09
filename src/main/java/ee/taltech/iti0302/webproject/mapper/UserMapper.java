package ee.taltech.iti0302.webproject.mapper;

import ee.taltech.iti0302.webproject.dto.user.PaginatedUserResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.dto.authentication.LoginResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.authentication.RegisterUserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toLoginUserDto(AppUser user, List<ProjectDto> projects);
    LoginResponseDto toLoginResponseDto(String authToken, String email, List<ProjectDto> projects, Integer id);
    AppUser toEntity(RegisterUserDto userDto);
    UserResponseDto toResponseDto(AppUser user);

    List<UserResponseDto> toResponseDtoList(List<AppUser> users);
    PaginatedUserResponseDto toPaginatedUserResponseDto(Integer totalPages, Integer page, Integer size, List<UserResponseDto> users);
}
