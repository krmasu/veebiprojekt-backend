package ee.taltech.iti0302.webproject.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedTaskDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<TaskDto> tasks;
}
