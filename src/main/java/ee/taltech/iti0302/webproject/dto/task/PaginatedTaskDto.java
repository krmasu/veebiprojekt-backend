package ee.taltech.iti0302.webproject.dto.task;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedTaskDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<TaskDto> tasks;
}
