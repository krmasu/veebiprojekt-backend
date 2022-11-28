package ee.taltech.iti0302.webproject.dto;

import lombok.Data;

@Data
public class CreateProjectDto {
    private Integer ownerId;
    private String title;
}
