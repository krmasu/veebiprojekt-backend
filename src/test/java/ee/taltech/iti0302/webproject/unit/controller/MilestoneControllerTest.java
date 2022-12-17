package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.MilestoneController;
import ee.taltech.iti0302.webproject.dto.milestone.CreateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.MilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.PaginatedMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.UpdateMilestoneDto;
import ee.taltech.iti0302.webproject.service.MilestoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MilestoneControllerTest {
    @Mock
    private MilestoneService milestoneService;
    @InjectMocks
    private MilestoneController milestoneController;

    @Test
    void getMilestones_GivenValidData_ReturnsPaginatedMilestoneDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                        .totalPages(1)
                        .page(0)
                        .size(20)
                        .milestones(List.of(MilestoneDto.builder().build()))
                        .build();
        given(milestoneService.getMilestones(1, null, pageable)).willReturn(paginatedMilestoneDto);
        // when
        var result = milestoneController.getMilestones(1, null, pageable);
        // then
        then(milestoneService).should().getMilestones(1, null, pageable);
        assertFalse(result.getMilestones().isEmpty());
    }

    @Test
    void createMilestone_GivenValidData_ReturnsPaginatedMilestoneDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        CreateMilestoneDto createMilestoneDto = CreateMilestoneDto.builder()
                .description("milestone")
                .title("milestone")
                .startDate(start)
                .endDate(end)
                .build();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .milestones(List.of(MilestoneDto.builder()
                        .id(1)
                        .description("milestone")
                        .title("milestone")
                        .startDate(start)
                        .endDate(end)
                        .build()))
                .build();
        given(milestoneService.createMilestone(1, createMilestoneDto, pageable)).willReturn(paginatedMilestoneDto);
        // when
        var result = milestoneController.createMilestone(1, createMilestoneDto, pageable);
        // then
        then(milestoneService).should().createMilestone(1, createMilestoneDto, pageable);
        assertEquals(paginatedMilestoneDto, result);
    }

    @Test
    void updateMilestone_GivenValidData_ReturnsPaginatedMilestoneDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusMonths(1);
        UpdateMilestoneDto updateMilestoneDto = UpdateMilestoneDto.builder()
                .description("milestone")
                .title("milestone")
                .startDate(start)
                .endDate(end)
                .build();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .milestones(List.of(MilestoneDto.builder()
                        .id(1)
                        .description("milestone")
                        .title("milestone")
                        .startDate(start)
                        .endDate(end)
                        .build()))
                .build();
        given(milestoneService.updateMilestone(1, 1, updateMilestoneDto, pageable)).willReturn(paginatedMilestoneDto);
        // when
        var result = milestoneController.updateMilestone(1, 1, updateMilestoneDto, pageable);
        // then
        then(milestoneService).should().updateMilestone(1, 1, updateMilestoneDto, pageable);
        assertEquals(paginatedMilestoneDto, result);
    }
    @Test
    void deleteMilestone_GivenValidData_ReturnsPaginatedMilestoneDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        PaginatedMilestoneDto paginatedMilestoneDto = PaginatedMilestoneDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .milestones(List.of())
                .build();
        given(milestoneService.deleteMilestone(1, 1, pageable)).willReturn(paginatedMilestoneDto);
        // when
        var result = milestoneController.deleteMilestone(1, 1, pageable);
        // then
        then(milestoneService).should().deleteMilestone(1, 1, pageable);
        assertEquals(paginatedMilestoneDto, result);
    }
}
