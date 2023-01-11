package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.milestone.CreateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.MilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.PaginatedMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.UpdateMilestoneDto;
import ee.taltech.iti0302.webproject.entity.Milestone;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ApplicationException;
import ee.taltech.iti0302.webproject.mapper.MilestoneMapper;
import ee.taltech.iti0302.webproject.mapper.MilestoneMapperImpl;
import ee.taltech.iti0302.webproject.repository.MilestoneRepository;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.service.MilestoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MilestoneServiceTest {
    @InjectMocks
    private MilestoneService milestoneService;
    @Mock
    private MilestoneRepository milestoneRepository;
    @Spy
    private MilestoneMapper milestoneMapper = new MilestoneMapperImpl();
    @Mock
    private ProjectRepository projectRepository;
    private static final Pageable UNPAGED = Pageable.unpaged();

    @Test
    void createMilestone_EndDateIsBeforeStartDate_ThrowsApplicationException() {
        // given
        CreateMilestoneDto createMilestoneDto = CreateMilestoneDto.builder()
                .startDate(LocalDate.now().plusDays(1))
                .endDate(LocalDate.now()).build();
        // when
        assertThrows(ApplicationException.class, () -> milestoneService.createMilestone(1, createMilestoneDto, UNPAGED));
    }

    @Test
    void createMilestone_ValidData_ReturnsPaginatedMilestoneDto() {
        // given
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);
        CreateMilestoneDto createMilestoneDto = CreateMilestoneDto.builder()
                .startDate(startDate)
                .endDate(endDate).build();
        Milestone milestone = Milestone.builder()
                .startDate(startDate)
                .endDate(endDate).build();
        Project project = Project.builder()
                .id(1).build();
        Page<Milestone> page = new PageImpl<>(List.of(milestone));
        MilestoneDto milestoneDto = MilestoneDto.builder()
                .startDate(startDate)
                .endDate(endDate).build();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                .milestones(List.of(milestoneDto)).build();
        Pageable pageable = Pageable.ofSize(5);
        given(milestoneMapper.toEntity(createMilestoneDto)).willReturn(milestone);
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(milestoneRepository.findAllByProjectId(1, pageable)).willReturn(page);
        given(milestoneMapper.toPaginatedDto(1, 0, 5, List.of(milestoneDto))).willReturn(paginatedMilestoneDto);

        // when
        var result = milestoneService.createMilestone(1, createMilestoneDto, pageable);

        // then
        then(milestoneMapper).should().toEntity(createMilestoneDto);
        then(projectRepository).should().findById(1);
        then(milestoneRepository).should().save(milestone);
        then(milestoneRepository).should().findAllByProjectId(1, pageable);
        then(milestoneMapper).should().toPaginatedDto(1, 0, 5, List.of(milestoneDto));

        assertEquals(1, result.getMilestones().size());
    }

    @Test
    void updateMilestone_EndDateIsBeforeStartDate_ThrowsApplicationException() {
        // given
        UpdateMilestoneDto updateMilestoneDto = UpdateMilestoneDto.builder()
                .endDate(LocalDate.now())
                .startDate(LocalDate.now().plusDays(1)).build();
        Milestone milestone = Milestone.builder().build();
        given(milestoneRepository.findById(1)).willReturn(Optional.of(milestone));

        // when
        assertThrows(ApplicationException.class, () -> milestoneService.updateMilestone(1, 1, updateMilestoneDto, UNPAGED));

        // then
        then(milestoneRepository).should().findById(1);
    }

    @Test
    void updateMilestone_ValidData_ReturnsPaginatedMilestoneDto() {
        // given
        UpdateMilestoneDto updateMilestoneDto = UpdateMilestoneDto.builder().build();
        Milestone milestone = Milestone.builder().build();
        Pageable pageable = Pageable.ofSize(5);
        Page<Milestone> page = new PageImpl<>(List.of(milestone));
        MilestoneDto milestoneDto = MilestoneDto.builder().build();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                .milestones(List.of(milestoneDto)).build();
        given(milestoneRepository.findById(1)).willReturn(Optional.of(milestone));
        given(milestoneRepository.findAllByProjectId(1, pageable)).willReturn(page);
        given(milestoneMapper.toPaginatedDto(1, 0, 5, List.of(milestoneDto))).willReturn(paginatedMilestoneDto);

        // when
        var result = milestoneService.updateMilestone(1, 1, updateMilestoneDto, pageable);

        // then
        then(milestoneRepository).should().findById(1);
        then(milestoneMapper).should().updateFromDto(updateMilestoneDto, milestone);
        then(milestoneRepository).should().findAllByProjectId(1, pageable);
        then(milestoneMapper).should().toPaginatedDto(1, 0, 5, List.of(milestoneDto));
        assertEquals(1, result.getMilestones().size());
    }

    @Test
    void deleteMilestone_ValidData_ReturnsPaginatedMilestoneDto() {
        // given
        Page<Milestone> page = Page.empty();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder().milestones(List.of()).build();
        given(milestoneRepository.findAllByProjectId(1, UNPAGED)).willReturn(page);
        given(milestoneMapper.toPaginatedDto(1, 0, 0, List.of())).willReturn(paginatedMilestoneDto);

        // when
        var result = milestoneService.deleteMilestone(1, 1, UNPAGED);

        // then
        then(milestoneRepository).should().deleteById(1);
        then(milestoneRepository).should().findAllByProjectId(1, UNPAGED);
        then(milestoneMapper).should().toPaginatedDto(1, 0, 0, List.of());
        assertTrue(result.getMilestones().isEmpty());
    }
}