package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.dto.project.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.project.DeleteProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.project.UpdateProjectDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.UserMapper;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public ProjectDto findById(Integer projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.orElseThrow(() -> new ResourceNotFoundException("Project to get not found"));
        return projectMapper.toDto(project);
    }

    public List<ProjectDto> createProject(CreateProjectDto createProjectDto) {
        Project project = new Project();
        project.setTitle(createProjectDto.getTitle());

        Integer userId = createProjectDto.getOwnerId();
        AppUser user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found when trying to create new project"));

        Project savedProject = projectRepository.save(project);
        user.getProjects().add(savedProject);

        return projectMapper.toDtoList(user.getProjects());
    }

    public List<ProjectDto> deleteById(DeleteProjectDto deleteProjectDto) {
        AppUser user = userRepository.findById(deleteProjectDto.getOwnerId()).orElseThrow(() -> new ResourceNotFoundException("User not found when trying to delete project"));
        Project project = projectRepository.findById(deleteProjectDto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project to delete not found"));

        user.getProjects().remove(project);
        projectRepository.deleteById(deleteProjectDto.getProjectId());
        return projectMapper.toDtoList(user.getProjects());
    }

    public ProjectDto updateProject(UpdateProjectDto updateProjectDto) {
        Project project = projectRepository.findById(updateProjectDto.getProjectId()).orElseThrow(() -> new ResourceNotFoundException("Project to update not found"));
        projectMapper.updateProjectFromDto(updateProjectDto, project);
        projectRepository.save(project);
        return projectMapper.toDto(project);
    }

    public List<UserResponseDto> getProjectMembers(Integer projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return userMapper.toResponseDtoList(project.getUsers());
    }

    public List<UserResponseDto> addNewProjectMembers(Integer projectId, AddMembersToProjectDto dto) {
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
        return userMapper.toResponseDtoList(projectMembers);
    }
}
