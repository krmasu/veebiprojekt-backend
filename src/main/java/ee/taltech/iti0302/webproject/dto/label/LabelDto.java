package ee.taltech.iti0302.webproject.dto.label;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LabelDto {
    private Integer id;
    private String title;
    private String colorCode;
}
