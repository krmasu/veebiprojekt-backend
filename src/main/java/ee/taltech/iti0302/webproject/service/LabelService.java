package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.label.UpdateLabelDto;
import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import ee.taltech.iti0302.webproject.dto.label.PaginatedLabelDto;
import ee.taltech.iti0302.webproject.entity.Label;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.LabelMapper;
import ee.taltech.iti0302.webproject.repository.LabelRepository;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LabelService {
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;
    private final ProjectRepository projectRepository;
    public PaginatedLabelDto createLabel(Integer projectId, UpdateLabelDto dto, Pageable pageable) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Label label = labelMapper.toEntity(dto);

        label.setProject(project);

        labelRepository.save(label);

        Page<Label> labels = labelRepository.findAllByProjectId(projectId, pageable);
        return labelMapper.toPaginatedDto(labels.getTotalPages(), labels.getNumber(), labels.getSize(), labelsToLabelDtos(labels.getContent()));
    }

    public List<LabelDto> labelsToLabelDtos(List<Label> labels) {
        List<LabelDto> dtos = new ArrayList<>();
        for (Label label : labels) {
            LabelDto dto = labelMapper.toDto(label);
            dtos.add(dto);
        }
        return dtos;
    }

    public PaginatedLabelDto getLabels(Integer projectId, Pageable pageable) {
        Page<Label> labels = labelRepository.findAllByProjectId(projectId, pageable);
        return labelMapper.toPaginatedDto(labels.getTotalPages(), labels.getNumber(), labels.getSize(), labelsToLabelDtos(labels.getContent()));
    }

    public PaginatedLabelDto updateLabel(Integer projectId, Integer labelId, UpdateLabelDto dto, Pageable pageable) {
        Label label = labelRepository.findById(labelId).orElseThrow(() -> new ResourceNotFoundException("Label to update not found"));

        labelMapper.updateLabelFromDto(dto, label);

        Page<Label> labels = labelRepository.findAllByProjectId(projectId, pageable);
        return labelMapper.toPaginatedDto(labels.getTotalPages(), labels.getNumber(), labels.getSize(), labelsToLabelDtos(labels.getContent()));
    }

    public PaginatedLabelDto deleteLabel(Integer projectId, Integer labelId, Pageable pageable) {
        labelRepository.deleteById(labelId);


        Page<Label> labels = labelRepository.findAllByProjectId(projectId, pageable);
        return labelMapper.toPaginatedDto(labels.getTotalPages(), labels.getNumber(), labels.getSize(), labelsToLabelDtos(labels.getContent()));
    }
}
