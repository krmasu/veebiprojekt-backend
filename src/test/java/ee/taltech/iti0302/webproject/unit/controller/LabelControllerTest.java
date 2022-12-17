package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.LabelController;
import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.dto.label.PaginatedLabelDto;
import ee.taltech.iti0302.webproject.dto.label.UpdateLabelDto;
import ee.taltech.iti0302.webproject.service.LabelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LabelControllerTest {
    @InjectMocks
    private LabelController labelController;

    @Mock
    private LabelService labelService;

    @Test
    void createLabel_GivenValidData_ReturnsPaginatedLabelDto() {
        UpdateLabelDto updateLabelDto = UpdateLabelDto.builder()
                .title("title")
                .colorCode("red")
                .build();
        Pageable pageable = Pageable.unpaged();
        LabelDto labelDto = LabelDto.builder()
                .id(1)
                .title("red label")
                .colorCode("red")
                .build();
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .labels(List.of(labelDto))
                .build();
        // given
        given(labelService.createLabel(1, updateLabelDto, pageable)).willReturn(paginatedLabelDto);

        // when
        var result = labelController.createLabel(1, updateLabelDto, pageable);

        // then
        then(labelService).should().createLabel(1, updateLabelDto, pageable);
        assertEquals(paginatedLabelDto.getLabels(), result.getLabels());
    }

    @Test
    void getLabels_ValidProjectId_ReturnsPaginatedLabelDto() {
        // given
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .labels(List.of(LabelDto.builder()
                        .id(1)
                        .colorCode("red")
                        .title("red label")
                        .build()))
                .build();
        Pageable pageable = Pageable.unpaged();
        given(labelService.getLabels(1, pageable)).willReturn(paginatedLabelDto);
        // when
        var result = labelController.getLabels(1, pageable);
        // then
        then(labelService).should().getLabels(1, pageable);
        assertEquals(20, result.getSize());
    }

    @Test
    void updateLabel_GivenValidData_ReturnsPaginatedLabelDto() {
        // given
        Pageable pageable = Pageable.unpaged();
        UpdateLabelDto updateLabelDto = UpdateLabelDto.builder()
                .title("yellow label")
                .colorCode("yellow")
                .build();
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .labels(List.of(LabelDto.builder()
                        .id(1)
                        .title("yellow label")
                        .colorCode("yellow")
                        .build()))
                .build();
        given(labelService.updateLabel(1, 1, updateLabelDto, pageable)).willReturn(paginatedLabelDto);
        // when
        var result = labelController.updateLabel(1, 1, updateLabelDto, pageable);
        // then
        then(labelService).should().updateLabel(1, 1, updateLabelDto, pageable);
        assertEquals("yellow label", result.getLabels().get(0).getTitle());
    }

    @Test
    void deleteLabel_GivenValidId_ReturnsPaginatedLabelDto() {
        // given
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder()
                .totalPages(1)
                .page(0)
                .size(20)
                .labels(List.of(LabelDto.builder()
                                .id(1)
                                .title("yellow label")
                                .colorCode("yellow")
                                .build()))
                .build();
        Pageable pageable = Pageable.unpaged();
        given(labelService.deleteLabel(1, 2, pageable)).willReturn(paginatedLabelDto);
        // when
        var result = labelController.deleteLabel(1, 2, pageable);
        // then
        then(labelService).should().deleteLabel(1, 2, pageable);
        assertEquals(1, result.getLabels().size());
    }


}