package ee.taltech.iti0302.webproject.dto;

import lombok.Data;

@Data
public class DeleteProjectDto {
    private Integer ownerId;
    private Integer projectId;
}
