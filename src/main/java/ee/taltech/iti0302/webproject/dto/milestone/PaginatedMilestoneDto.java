package ee.taltech.iti0302.webproject.dto.milestone;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedMilestoneDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<MilestoneDto> milestones;
}
