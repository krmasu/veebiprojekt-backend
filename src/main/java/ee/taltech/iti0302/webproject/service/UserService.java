package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.user.PaginatedUserResponseDto;
import ee.taltech.iti0302.webproject.dto.user.UserDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProjectRepository projectRepository;

    public UserDto getUserData(Integer userId) {
        Optional<AppUser> optionalUser = userRepository.findById(userId);
        AppUser user = optionalUser.orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Project> projects = user.getProjects();
        List<ProjectDto> projectDtoList = projectMapper.toDtoList(projects);
        return userMapper.toLoginUserDto(user, projectDtoList);
    }

    public PaginatedUserResponseDto getProjectMembers(Integer projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found."));
        Page<AppUser> users = userRepository.findAllByProjectsContains(project, pageable);
        return userMapper.toPaginatedUserResponseDto(users.getTotalPages(), users.getNumber(), users.getSize(), userMapper.toResponseDtoList(users.getContent()));
    }

    public PaginatedUserResponseDto addNewProjectMembers(Integer projectId, AddMembersToProjectDto dto, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        List<AppUser> projectMembers = project.getUsers();
        List<Integer> memberIds = projectMembers.stream()
                .map(AppUser::getId)
                .toList();
        List<Integer> toAddIds = dto.getUserIds().stream()
                .filter(id -> !memberIds.contains(id))
                .toList();
        for (Integer userId : toAddIds) {
            AppUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User to add as member not found"));
            user.getProjects().add(project);
            projectMembers.add(user);
        }
        Page<AppUser> users = userRepository.findAllByProjectsContains(project, pageable);
        return userMapper.toPaginatedUserResponseDto(users.getTotalPages(), users.getNumber(), users.getSize(), userMapper.toResponseDtoList(users.getContent()));

    }
}
