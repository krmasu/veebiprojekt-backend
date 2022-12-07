package ee.taltech.iti0302.webproject.dto.label;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedLabelDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<LabelDto> labels;
}
