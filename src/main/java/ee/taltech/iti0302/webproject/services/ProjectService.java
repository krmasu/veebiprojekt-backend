package ee.taltech.iti0302.webproject.services;

import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public Optional<ProjectDto> findById(Integer projectId) {
        return projectRepository.findById(projectId).map(projectMapper::toDto);
    }
}
