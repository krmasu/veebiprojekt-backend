package ee.taltech.iti0302.webproject.dto.user;

import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private List<ProjectDto> projects;
}
