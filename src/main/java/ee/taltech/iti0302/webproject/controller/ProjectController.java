package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("api/project/{projectId}")
    public ProjectDto getProjectById(@PathVariable("projectId") Integer projectId) {
        return projectService.findById(projectId);
    }
}
