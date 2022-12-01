package ee.taltech.iti0302.webproject.dto.authentication;

import ee.taltech.iti0302.webproject.dto.project.ProjectDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponseDto {
    private String authToken;
    private String email;
    private List<ProjectDto> projects;
    private Integer id;
}
