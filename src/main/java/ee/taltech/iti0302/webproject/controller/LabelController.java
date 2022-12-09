package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.label.UpdateLabelDto;
import ee.taltech.iti0302.webproject.dto.label.PaginatedLabelDto;
import ee.taltech.iti0302.webproject.service.LabelService;
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
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LabelController {
    private final LabelService labelService;
    private static final Logger log = LoggerFactory.getLogger(LabelController.class);

    @PostMapping("api/project/{projectId}/label")
    public PaginatedLabelDto createLabel(@PathVariable("projectId") Integer projectId,
                                         @RequestBody UpdateLabelDto dto,
                                         Pageable pageable) {
        log.info("Creating label for project with id: {}", projectId);
        return labelService.createLabel(projectId, dto, pageable);
    }

    @GetMapping("api/project/{projectId}/label")
    public PaginatedLabelDto getLabels(@PathVariable("projectId") Integer projectId,
                                       Pageable pageable) {
        log.info("Getting labels for project with id: {}", projectId);
        return labelService.getLabels(projectId, pageable);
    }

    @PatchMapping("api/project/{projectId}/label/{labelId}")
    public PaginatedLabelDto updateLabel(@PathVariable("projectId") Integer projectId,
                                         @PathVariable("labelId") Integer labelId,
                                         @RequestBody UpdateLabelDto dto,
                                         Pageable pageable) {
        log.info("Updating label with id: {}", labelId);
        return labelService.updateLabel(projectId, labelId, dto, pageable);
    }

    @DeleteMapping("api/project/{projectId}/label/{labelId}")
    public PaginatedLabelDto deleteLabel(@PathVariable("projectId") Integer projectId,
                                         @PathVariable("labelId") Integer labelId,
                                         Pageable pageable) {
        log.info("Deleting label with id: {}", labelId);
        return labelService.deleteLabel(projectId, labelId, pageable);
    }


}
