package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.ProjectController;
import ee.taltech.iti0302.webproject.dto.project.AddMembersToProjectDto;
import ee.taltech.iti0302.webproject.dto.project.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.project.DeleteProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.dto.project.UpdateProjectDto;
import ee.taltech.iti0302.webproject.dto.user.UserResponseDto;
import ee.taltech.iti0302.webproject.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {
    @Mock
    private ProjectService projectService;
    @InjectMocks
    private ProjectController projectController;
    private final Principal principal = () -> String.valueOf(1);

    @Test
    void GetProjectById_Valid_ReturnsProjectDto() {
        // given
        ProjectDto projectDto = ProjectDto.builder()
                        .id(1)
                        .title("title")
                        .build();
        given(projectService.findById(1)).willReturn(projectDto);
        // when
        var result = projectController.getProjectById(1, principal);
        // then
        then(projectService).should().findById(1);
        assertEquals("title", result.getTitle());
        assertEquals(1, result.getId());
    }

    @Test
    void CreateProject_Valid_ReturnsListOfUserProjectsDtos() {
        // given
        CreateProjectDto createProjectDto = CreateProjectDto.builder()
                .ownerId(2)
                .title("title2")
                .build();
        List<ProjectDto> projectDtos = new ArrayList<>();
        ProjectDto projectDto1 = ProjectDto.builder()
                        .id(1)
                        .title("title1")
                        .build();
        ProjectDto projectDto2 = ProjectDto.builder()
                .id(2)
                .title("title2")
                .build();
        projectDtos.add(projectDto1);
        projectDtos.add(projectDto2);
        given(projectService.createProject(createProjectDto)).willReturn(projectDtos);
        // when
        var result = projectController.createProject(createProjectDto, principal);
        // then
        then(projectService).should().createProject(createProjectDto);
        assertEquals(2, result.size());
        assertEquals("title1", result.get(0).getTitle());
    }

    @Test
    void UpdateProject_Valid_ReturnsUpdatedProject() {
        // given
        UpdateProjectDto updateProjectDto = UpdateProjectDto.builder()
                .projectId(1)
                .title("project")
                .build();
        ProjectDto projectDto = ProjectDto.builder()
                        .id(1)
                        .title("project")
                        .build();
        given(projectService.updateProject(updateProjectDto)).willReturn(projectDto);
        // when
        var result = projectController.updateProject(updateProjectDto, principal);
        // then
        then(projectService).should().updateProject(updateProjectDto);
        assertEquals(1, result.getId());
        assertEquals("project", result.getTitle());
    }

    @Test
    void DeleteProject_NoOtherProjects_ReturnsEmptyList() {
        // given
        DeleteProjectDto deleteProjectDto = DeleteProjectDto.builder()
                .ownerId(1)
                .projectId(1)
                .build();
        List<ProjectDto> projectDtos = new ArrayList<>();
        given(projectService.deleteById(deleteProjectDto)).willReturn(projectDtos);
        // when
        var result = projectController.deleteProject(deleteProjectDto);
        // then
        then(projectService).should().deleteById(deleteProjectDto);
        assertTrue(result.isEmpty());
    }

    @Test
    void DeleteProject_OtherProjectsExist_ReturnsRemainingProjects() {
        // given
        DeleteProjectDto deleteProjectDto = DeleteProjectDto.builder()
                .ownerId(1)
                .projectId(3)
                .build();
        ProjectDto projectDt01 = ProjectDto.builder()
                .id(1)
                .title("project1")
                .build();
        ProjectDto projectDto2 = ProjectDto.builder()
                .id(2)
                .title("project2")
                .build();
        List<ProjectDto> projectDtos = new ArrayList<>();
        projectDtos.add(projectDt01);
        projectDtos.add(projectDto2);

        given(projectService.deleteById(deleteProjectDto)).willReturn(projectDtos);
        // when
        var result = projectController.deleteProject(deleteProjectDto);
        // then
        then(projectService).should().deleteById(deleteProjectDto);
        assertEquals(2, result.size());
    }
}