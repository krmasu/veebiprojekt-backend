package ee.taltech.iti0302.webproject.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto {
    private String title;
    private String description;
    private LocalDate deadline;
    private Integer assigneeId;
    private Integer statusId;
    private List<Integer> labelIds;
    private Integer milestoneId;
}
