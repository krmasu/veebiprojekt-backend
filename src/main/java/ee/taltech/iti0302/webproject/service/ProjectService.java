package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.DeleteProjectDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
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
}
