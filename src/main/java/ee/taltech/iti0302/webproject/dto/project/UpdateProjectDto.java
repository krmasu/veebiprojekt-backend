package ee.taltech.iti0302.webproject.dto.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProjectDto {
    private Integer projectId;
    private String title;
    // Add any variable from Project entity here to be able to update that variable
    // CAN'T UPDATE OTHER ENTITIES THROUGH THIS DTO
}
