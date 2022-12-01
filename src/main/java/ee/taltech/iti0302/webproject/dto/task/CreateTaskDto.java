package ee.taltech.iti0302.webproject.dto.task;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateTaskDto {
    private String title;
    private String description;
    // yyyy-mm-dd
    private LocalDate deadline;

    private Integer projectId;
    private Integer assigneeId;
    private Integer statusId;
    private Integer milestoneId;
    private List<Integer> labelIds;
}
