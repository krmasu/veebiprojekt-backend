package ee.taltech.iti0302.webproject.dto.task;

import ee.taltech.iti0302.webproject.dto.label.LabelDto;
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
public class TaskDto {
    private String title;
    private String description;
    // yyyy-mm-dd
    private LocalDate deadline;

    private Integer projectId;
    private AssigneeDto assignee;
    private Integer statusId;
    private Integer milestoneId;
    private List<LabelDto> labels;
}
