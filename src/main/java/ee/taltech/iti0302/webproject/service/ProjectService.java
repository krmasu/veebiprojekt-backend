package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectDto findById(Integer projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        Project project = projectOptional.orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        return projectMapper.toDto(project);
    }
}
