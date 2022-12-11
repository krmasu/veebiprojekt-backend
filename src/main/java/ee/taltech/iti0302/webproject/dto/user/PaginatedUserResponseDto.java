package ee.taltech.iti0302.webproject.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedUserResponseDto {
    private Integer totalPages;
    private Integer page;
    private Integer size;
    private List<UserResponseDto> users;
}
