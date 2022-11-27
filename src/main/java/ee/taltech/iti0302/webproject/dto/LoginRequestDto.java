package ee.taltech.iti0302.webproject.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
