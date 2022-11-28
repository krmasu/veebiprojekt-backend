package ee.taltech.iti0302.webproject.dto.authentication;

import lombok.Data;

@Data
public class RegisterUserDto {
    private String username;
    private String password;
    private String email;
}
