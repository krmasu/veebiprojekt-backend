package ee.taltech.iti0302.webproject.dto.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponseDto {
    private String authToken;
    private String email;
    private Integer id;
    private String message;
    private boolean ok;
}
