package ee.taltech.iti0302.webproject.unit.mapper;

import ee.taltech.iti0302.webproject.entity.Label;
import ee.taltech.iti0302.webproject.mapper.LabelMapper;
import ee.taltech.iti0302.webproject.mapper.LabelMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LabelMapperImplTest {
    private LabelMapper labelMapper = new LabelMapperImpl();
    @Test
    void toDtoList_ValidData_ReturnsNull() {
        Label label = Label.builder()
                .id(1)
                .title("title")
                .colorCode("red")
                .build();
        Label label2 = Label.builder()
                .id(2)
                .title("title2")
                .colorCode("yellow")
                .build();
        var result = labelMapper.toDtoList(List.of(label, label2));

        assertEquals("yellow", result.get(1).getColorCode());
    }
}