package ee.taltech.iti0302.webproject.dto;

import lombok.Data;

import java.util.Map;

@Data
public class LoginUserDto {
    private Integer id;
    private String username;
    private String email;
    private Map<Integer, String> projects;
}
