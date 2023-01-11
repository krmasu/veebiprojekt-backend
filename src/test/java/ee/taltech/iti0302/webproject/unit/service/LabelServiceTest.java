package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.dto.label.PaginatedLabelDto;
import ee.taltech.iti0302.webproject.dto.label.UpdateLabelDto;
import ee.taltech.iti0302.webproject.entity.Label;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.mapper.LabelMapper;
import ee.taltech.iti0302.webproject.mapper.LabelMapperImpl;
import ee.taltech.iti0302.webproject.repository.LabelRepository;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.service.LabelService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {
    @InjectMocks
    private LabelService labelService;
    @Mock
    private LabelRepository labelRepository;
    @Spy
    private LabelMapper labelMapper = new LabelMapperImpl();
    @Mock
    private ProjectRepository projectRepository;

    @Test
    void createLabel_ValidData_ReturnsPaginatedLabelDto() {
        // given
        UpdateLabelDto updateLabelDto = UpdateLabelDto.builder()
                .title("title")
                .colorCode("red")
                .build();
        Pageable pageable = Pageable.ofSize(5);
        Project project = Project.builder().id(1).build();
        Label label = Label.builder().title("title").colorCode("red").id(1).build();
        LabelDto labelDto = LabelDto.builder().id(1).title("title").colorCode("red").build();
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder().labels(List.of(labelDto)).build();
        Page<Label> labelPage = new PageImpl<>(List.of(label));
        given(projectRepository.findById(1)).willReturn(Optional.of(project));
        given(labelMapper.toEntity(updateLabelDto)).willReturn(label);
        given(labelRepository.findAllByProjectId(1, pageable)).willReturn(labelPage);
        given(labelMapper.toDto(label)).willReturn(labelDto);
        given(labelMapper.toPaginatedDto(1, 0, 1, List.of(labelDto))).willReturn(paginatedLabelDto);

        // when
        var result = labelService.createLabel(1, updateLabelDto, pageable);

        // then
        assertEquals(labelDto, result.getLabels().get(0));
        then(projectRepository).should().findById(1);
        then(labelMapper).should().toEntity(updateLabelDto);
        then(labelRepository).should().save(label);
        then(labelRepository).should().findAllByProjectId(1, pageable);
        then(labelMapper).should().toPaginatedDto(1, 0, 1, List.of(labelDto));
    }

    @Test
    void getLabels_ValidData_ReturnsPaginatedLabelDto() {
        // given
        Label label = Label.builder().title("title").colorCode("red").id(1).build();
        LabelDto labelDto = LabelDto.builder().id(1).title("title").colorCode("red").build();
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder().labels(List.of(labelDto)).build();
        Page<Label> labelPage = new PageImpl<>(List.of(label));
        Pageable pageable = Pageable.ofSize(5);
        given(labelRepository.findAllByProjectId(1, pageable)).willReturn(labelPage);
        given(labelMapper.toDto(label)).willReturn(labelDto);
        given(labelMapper.toPaginatedDto(1, 0, 1, List.of(labelDto))).willReturn(paginatedLabelDto);

        // when
        var result = labelService.getLabels(1, pageable);

        // then
        then(labelRepository).should().findAllByProjectId(1, pageable);
        then(labelMapper).should().toPaginatedDto(1, 0, 1, List.of(labelDto));
        assertEquals(1, result.getLabels().size());
    }

    @Test
    void updateLabel_AllVariables_ReturnsPaginedLabelDto() {
        // given
        UpdateLabelDto updateLabelDto = UpdateLabelDto.builder()
                .colorCode("red")
                .title("new title")
                .build();
        Label label = Label.builder().id(1).colorCode("yellow").title("title").build();
        Pageable pageable = Pageable.ofSize(5);
        Page<Label> page = new PageImpl<>(List.of(label));
        LabelDto labelDto = LabelDto.builder()
                .colorCode("red")
                .title("new title")
                .build();
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder()
                .labels(List.of(labelDto)).build();
        given(labelRepository.findById(1)).willReturn(Optional.of(label));
        given(labelRepository.findAllByProjectId(1, pageable)).willReturn(page);
        given(labelMapper.toPaginatedDto(1, 0 , 1, List.of(labelDto))).willReturn(paginatedLabelDto);
        given(labelMapper.toDto(label)).willReturn(labelDto);

        // when
        var result = labelService.updateLabel(1, 1, updateLabelDto, pageable);

        // then
        then(labelRepository).should().findById(1);
        then(labelMapper).should().updateLabelFromDto(updateLabelDto, label);
        then(labelRepository).should().findAllByProjectId(1, pageable);
        then(labelMapper).should().toPaginatedDto(1, 0, 1, List.of(labelDto));
        assertEquals("red", result.getLabels().get(0).getColorCode());
    }

    @Test
    void deleteLabel_ValidData_ReturnsPaginatedLabelDto() {
        // given
        Page<Label> page = Page.empty();
        Pageable pageable = Pageable.ofSize(5);
        PaginatedLabelDto paginatedLabelDto = PaginatedLabelDto.builder().labels(List.of()).build();
        given(labelRepository.findAllByProjectId(1, pageable)).willReturn(page);
        given(labelMapper.toPaginatedDto(1, 0, 0, List.of())).willReturn(paginatedLabelDto);

        // when
        var result = labelService.deleteLabel(1, 1, pageable);

        // then
        then(labelRepository).should().deleteById(1);
        then(labelRepository).should().findAllByProjectId(1, pageable);
        then(labelMapper).should().toPaginatedDto(1, 0, 0, List.of());
        assertTrue(result.getLabels().isEmpty());
    }
}