package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.UpdateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.CreateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.PaginatedMilestoneDto;
import ee.taltech.iti0302.webproject.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;
    private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);

    @GetMapping("api/project/{projectId}/milestone")
    public PaginatedMilestoneDto getMilestones(@PathVariable("projectId") Integer projectId,
                                               @RequestParam(name = "title", required = false) String title,
                                               Pageable pageable) {
            log.info("Getting milestones for project with id: {}", projectId);
            return milestoneService.getMilestones(projectId, title, pageable);
    }

    @PostMapping("api/project/{projectId}/milestone")
    public PaginatedMilestoneDto createMilestone(@PathVariable("projectId") Integer projectId,
                                                 @RequestBody CreateMilestoneDto dto,
                                                 Pageable pageable) {
        log.info("Creating milestone for project with id: {}", projectId);
        return milestoneService.createMilestone(projectId, dto, pageable);
    }

    @PatchMapping("api/project/{projectId}/milestone/{milestoneId}")
    public PaginatedMilestoneDto updateMilestone(@PathVariable("projectId") Integer projectId,
                                                 @PathVariable("milestoneId") Integer milestoneId,
                                                 @RequestBody UpdateMilestoneDto updateMilestoneDto,
                                                 Pageable pageable) {
        log.info("Updating milestone with id: {} from project with id: {}", milestoneId, projectId);
        return milestoneService.updateMilestone(projectId, milestoneId, updateMilestoneDto, pageable);
    }

    @DeleteMapping("api/project/{projectId}/milestone/{milestoneId}")
    public PaginatedMilestoneDto deleteMilestone(@PathVariable("projectId") Integer projectId,
                                                 @PathVariable("milestoneId") Integer milestoneId,
                                                 Pageable pageable) {
        log.info("Deleting milestone with id: {} from project with id: {}", milestoneId, projectId);
        return milestoneService.deleteMilestone(projectId, milestoneId, pageable);
    }
}
