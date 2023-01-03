package ee.taltech.iti0302.webproject.dto.milestone;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginatedMilestoneDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<MilestoneDto> milestones;
}
