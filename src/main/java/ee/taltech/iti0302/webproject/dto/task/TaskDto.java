package ee.taltech.iti0302.webproject.dto.task;

import ee.taltech.iti0302.webproject.dto.label.LabelDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TaskDto {
    private String title;
    private String description;
    // yyyy-mm-dd
    private LocalDate deadline;
    
    private Integer id;
    private Integer projectId;
    private AssigneeDto assignee;
    private Integer statusId;
    private Integer milestoneId;
    private List<LabelDto> labels;
}
