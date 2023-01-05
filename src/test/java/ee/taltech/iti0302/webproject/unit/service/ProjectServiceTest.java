package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.project.CreateProjectDto;
import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import ee.taltech.iti0302.webproject.entity.AppUser;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.mapper.ProjectMapper;
import ee.taltech.iti0302.webproject.mapper.ProjectMapperImpl;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.repository.UserRepository;
import ee.taltech.iti0302.webproject.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {
    @InjectMocks
    private ProjectService projectService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectMapper projectMapper;
    @Mock
    private UserRepository userRepository;

    @Test
    void findById_ValidId_ReturnsProjectDto() {
        // given
        Project project = Project.builder().id(1).build();
        ProjectDto projectDto = ProjectDto.builder().id(1).build();
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(projectMapper.toDto(project)).willReturn(projectDto);
        // when
        var result = projectService.findById(1);

        // then
        then(projectRepository).should().findById(1);
        then(projectMapper).should().toDto(project);
        assertEquals(1, result.getId());
    }

    @Test
    void createProject_ValidData_ReturnsListOfProjectDtos() {
        // given
        CreateProjectDto createProjectDto = CreateProjectDto.builder().title("title").ownerId(1).build();
        Project project = Project.builder().id(1).title("title").build();
        AppUser user = AppUser.builder().projects(new ArrayList<>()).build();
        ProjectDto projectDto = ProjectDto.builder().id(1).title("title").build();
        given(projectMapper.toEntity(createProjectDto)).willReturn(project);
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(projectRepository.save(project)).willReturn(project);
        given(projectMapper.toDtoList(List.of(project))).willReturn(List.of(projectDto));

        // when
        var result = projectService.createProject(createProjectDto);

        // then
        then(projectMapper).should().toEntity(createProjectDto);
        then(userRepository).should().findById(1);
        then(projectRepository).should().save(project);
        then(projectMapper).should().toDtoList(List.of(project));
        assertEquals(1, result.get(0).getId());
    }
}