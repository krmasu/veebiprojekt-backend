package ee.taltech.iti0302.webproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedDto {
    private String message;
    private boolean ok;
}


