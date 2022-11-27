package ee.taltech.iti0302.webproject.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponseDto {
    private String authToken;
    private String email;
    private List<ProjectDto> projects;
    private Integer id;
}
