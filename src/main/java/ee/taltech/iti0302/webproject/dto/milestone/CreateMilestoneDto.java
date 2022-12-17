package ee.taltech.iti0302.webproject.dto.milestone;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMilestoneDto {
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
}

