package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.DeleteProjectDto;
import ee.taltech.iti0302.webproject.dto.ProjectDto;
import ee.taltech.iti0302.webproject.dto.UpdateProjectDto;
import ee.taltech.iti0302.webproject.dto.UserResponseDto;
import ee.taltech.iti0302.webproject.dto.project.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.project.DeleteProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.project.UpdateProjectDto;
import ee.taltech.iti0302.webproject.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ProjectDto getProjectById(@PathVariable("projectId") Integer projectId, Principal principal) {
        log.info("Getting project with id: {} for user with id: {}", projectId, principal.getName());
        return projectService.findById(projectId);
    }

    @PostMapping("api/project")
    public List<ProjectDto> createProject(@RequestBody CreateProjectDto createProjectDto, Principal principal) {
        log.info("Creating new project for user with id: {}", principal.getName());
        return projectService.createProject(createProjectDto);
    }

    @PatchMapping("api/project")
    public ProjectDto updateProject(@RequestBody UpdateProjectDto updateProjectDto, Principal principal) {
        log.info("Updating project with id: {} for user with id: {}", updateProjectDto.getProjectId(), principal.getName());
        return projectService.updateProject(updateProjectDto);
    }


    @DeleteMapping("api/project")
    public List<ProjectDto> deleteProject(@RequestBody DeleteProjectDto deleteProjectDto) {
        log.info("Deleting project with id: {} for user with id: {}", deleteProjectDto.getProjectId(), deleteProjectDto.getOwnerId());
        return projectService.deleteById(deleteProjectDto);
    }

    @GetMapping("api/project/{projectId}/member")
    public List<UserResponseDto> getProjectMembers(@PathVariable("projectId") Integer projectId, Principal principal) {
        log.info("Getting members for project with id: {} for user with id: {}", projectId, principal.getName());
        return projectService.getProjectMembers(projectId);
    }

    @PostMapping("api/project/{projectId}/member")
    public List<UserResponseDto> addNewProjectMembers(@PathVariable("projectId") Integer projectId, @RequestBody AddMembersToProjectDto dto) {
        log.info("Adding new members to project with id: {}", dto);
        return projectService.addNewProjectMembers(projectId, dto);
    }
}
