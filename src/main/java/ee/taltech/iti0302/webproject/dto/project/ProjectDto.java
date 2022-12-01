package ee.taltech.iti0302.webproject.dto.project;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {
    private Integer id;
    private String title;
}
