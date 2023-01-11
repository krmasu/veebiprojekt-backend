package ee.taltech.iti0302.webproject.unit.mapper;

import ee.taltech.iti0302.webproject.entity.Milestone;
import ee.taltech.iti0302.webproject.mapper.MilestoneMapper;
import ee.taltech.iti0302.webproject.mapper.MilestoneMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MilestoneMapperImplTest {
    private MilestoneMapper milestoneMapper = new MilestoneMapperImpl();

    @Test
    void toDtoList_ValidData_ReturnsListOfMilestoneDtos() {
        Milestone milestone1 = Milestone.builder()
                .id(1)
                .title("title1")
                .build();
        Milestone milestone2 = Milestone.builder()
                .id(2)
                .title("title2")
                .build();
        var result = milestoneMapper.toDtoList(List.of(milestone1, milestone2));

        assertEquals("title2", result.get(1).getTitle());
    }
}