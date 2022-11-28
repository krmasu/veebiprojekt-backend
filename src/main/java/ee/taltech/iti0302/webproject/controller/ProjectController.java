package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.UpdateProjectDto;
import ee.taltech.iti0302.webproject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RequiredArgsConstructor
@RestController
public class ProjectController {
    private final ProjectService projectService;
    private static final Logger log = LoggerFactory.getLogger(ProjectController.class);

    @GetMapping("api/project/{projectId}")
    public ProjectDto getProjectById(@PathVariable("projectId") Integer projectId) {
        return projectService.findById(projectId);
    }

    @PostMapping("api/project")
    public List<ProjectDto> createProject(@RequestBody CreateProjectDto createProjectDto) {
        return projectService.createProject(createProjectDto);
    }

    @PatchMapping("api/project")
    public ProjectDto updateProject(@RequestBody UpdateProjectDto updateProjectDto, Principal principal) {
        log.info("Updating project with id: {} for user with id: {}", updateProjectDto.getProjectId(), principal.getName());
        return projectService.updateProject(updateProjectDto);
    }

}
