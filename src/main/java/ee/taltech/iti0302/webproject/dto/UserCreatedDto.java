package ee.taltech.iti0302.webproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCreatedDto {
    private String message;
    private boolean ok;
}


