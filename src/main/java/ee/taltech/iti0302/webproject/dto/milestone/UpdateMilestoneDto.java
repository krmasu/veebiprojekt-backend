package ee.taltech.iti0302.webproject.dto.milestone;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateMilestoneDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}
