package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.authentication.LoginUserDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
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
        AppUser user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Project> projects = user.getProjects();
        List<ProjectDto> projectDtoList = projectMapper.toDtoList(projects);
        return userMapper.toLoginUserDto(user, projectDtoList);
    }

}
