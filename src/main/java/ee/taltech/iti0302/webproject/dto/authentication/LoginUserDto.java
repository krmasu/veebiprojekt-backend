package ee.taltech.iti0302.webproject.dto.authentication;

import ee.taltech.iti0302.webproject.dto.ProjectDto;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserDto {
    private Integer id;
    private String username;
    private String email;
    private List<ProjectDto> projects;
}
