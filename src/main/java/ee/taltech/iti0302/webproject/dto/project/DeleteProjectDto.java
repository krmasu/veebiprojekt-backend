package ee.taltech.iti0302.webproject.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteProjectDto {
    private Integer ownerId;
    private Integer projectId;
}
