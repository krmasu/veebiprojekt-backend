package ee.taltech.iti0302.webproject.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
    private Integer id;
    private String username;
}
