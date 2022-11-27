package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.UserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.InvalidCredentialsException;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    public LoginUserDto getUserData(UserDto request) {
        Integer id = request.getId();
        Optional<AppUser> optionalUser = userRepository.findById(id);
        AppUser user = optionalUser.orElseThrow(() -> new InvalidCredentialsException(InvalidCredentialsException.Reason.ID));

        List<Project> projects = user.getProjects();
        List<ProjectDto> projectDtoList = projectMapper.toDtoList(projects);
        return userMapper.toLoginUserDto(user, projectDtoList);
    }

}
