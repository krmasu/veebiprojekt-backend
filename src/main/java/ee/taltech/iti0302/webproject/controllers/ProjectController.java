package ee.taltech.iti0302.webproject.controllers;

import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("api/project/{projectId}")
    public Optional<ProjectDto> getEmployeeById(@PathVariable("projectId") Integer projectId) {
        return projectService.findById(projectId);
    }
}
