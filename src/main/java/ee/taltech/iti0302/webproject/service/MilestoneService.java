package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.milestone.UpdateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.CreateMilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.MilestoneDto;
import ee.taltech.iti0302.webproject.dto.milestone.PaginatedMilestoneDto;
import ee.taltech.iti0302.webproject.entity.Milestone;
import ee.taltech.iti0302.webproject.entity.Project;
import ee.taltech.iti0302.webproject.exception.ResourceNotFoundException;
import ee.taltech.iti0302.webproject.mapper.MilestoneMapper;
import ee.taltech.iti0302.webproject.repository.MilestoneRepository;
import ee.taltech.iti0302.webproject.repository.ProjectRepository;
import ee.taltech.iti0302.webproject.specification.MilestoneSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final MilestoneMapper milestoneMapper;
    private final ProjectRepository projectRepository;
    public PaginatedMilestoneDto getMilestones(Integer projectId, String title, Pageable pageable) {
        Specification<Milestone> specification = Specification
                .where(MilestoneSpecification.byProject(projectId))
                .and(title == null ? null : MilestoneSpecification.titleContains(title));
        Page<Milestone> milestones = milestoneRepository.findAll(specification, pageable);
        return milestoneMapper.toPaginatedDto(milestones.getTotalPages(), pageable.getPageNumber(), pageable.getPageSize(), milestonesToMilestoneDtos(milestones.getContent()));
    }

    private List<MilestoneDto> milestonesToMilestoneDtos(List<Milestone> milestones) {
        List<MilestoneDto> dtos = new ArrayList<>();
        for (Milestone milestone : milestones) {
            dtos.add(milestoneMapper.toDto(milestone));
        }
        return dtos;
    }

    public PaginatedMilestoneDto createMilestone(Integer projectId, CreateMilestoneDto dto, Pageable pageable) {
        Milestone milestone = milestoneMapper.toEntity(dto);

        Project project = projectRepository.findById(projectId).orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        milestone.setProject(project);

        milestoneRepository.save(milestone);

        Page<Milestone> milestones = milestoneRepository.findAllByProjectId(projectId, pageable);
        return milestoneMapper.toPaginatedDto(milestones.getTotalPages(), pageable.getPageNumber(), pageable.getPageSize(), milestonesToMilestoneDtos(milestones.getContent()));
    }

    public PaginatedMilestoneDto updateMilestone(Integer projectId, Integer milestoneId, UpdateMilestoneDto updateMilestoneDto, Pageable pageable) {
        Milestone milestone = milestoneRepository.findById(milestoneId).orElseThrow(() -> new ResourceNotFoundException("Milestone to update not found"));

        milestoneMapper.updateFromDto(updateMilestoneDto, milestone);

        Page<Milestone> milestones = milestoneRepository.findAllByProjectId(projectId, pageable);
        return milestoneMapper.toPaginatedDto(milestones.getTotalPages(), pageable.getPageNumber(), pageable.getPageSize(), milestonesToMilestoneDtos(milestones.getContent()));
    }

    public PaginatedMilestoneDto deleteMilestone(Integer projectId, Integer milestoneId, Pageable pageable) {
        milestoneRepository.deleteById(milestoneId);
        Page<Milestone> milestones = milestoneRepository.findAllByProjectId(projectId, pageable);
        return milestoneMapper.toPaginatedDto(milestones.getTotalPages(), pageable.getPageNumber(), pageable.getPageSize(), milestonesToMilestoneDtos(milestones.getContent()));
    }
}
