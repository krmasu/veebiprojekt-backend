package ee.taltech.iti0302.webproject.dto.project;

import lombok.Data;

import java.util.List;

@Data
public class AddMembersToProjectDto {
    private List<Integer> userIds;
}
