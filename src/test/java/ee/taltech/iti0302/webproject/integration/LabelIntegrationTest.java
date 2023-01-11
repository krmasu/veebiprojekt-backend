package ee.taltech.iti0302.webproject.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LabelIntegrationTest extends AbstractIntegrationTestClass {
    @Autowired
    private MockMvc mvc;

    @Test
    void testLabelEndpoints() throws Exception {
        createLabel_LabelIsCreatedCorrectly_ReturnsProjectLabels();
        getLabels_LabelExists_ReturnsProjectLabels();
        updateLabel_LabelExists_ReturnsUpdatedProjectLabels();
        deleteLabel_LabelExists_ReturnsEmpty();
    }


    private void createLabel_LabelIsCreatedCorrectly_ReturnsProjectLabels() throws Exception {
        String inputContent = "{ \"title\": \"test_label2\", \"colorCode\": \"000000\"}";

        Map<String, Object> outputLabel = new LinkedHashMap<>();
        outputLabel.put("id", 1);
        outputLabel.put("title", "test_label");
        outputLabel.put("colorCode", "FFFFFF");

        Map<String, Object> outputLabel2 = new LinkedHashMap<>();
        outputLabel2.put("id", 2);
        outputLabel2.put("title", "test_label2");
        outputLabel2.put("colorCode", "000000");

        mvc.perform(post("/api/project/1/label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.labels").isArray())
                .andExpect(jsonPath("$.labels[0]", is(outputLabel)))
                .andExpect(jsonPath("$.labels[1]", is(outputLabel2)));
    }


    private void getLabels_LabelExists_ReturnsProjectLabels() throws Exception {
        Map<String, Object> outputLabel = new LinkedHashMap<>();
        outputLabel.put("id", 1);
        outputLabel.put("title", "test_label");
        outputLabel.put("colorCode", "FFFFFF");

        mvc.perform(get("/api/project/1/label")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.labels").isArray())
                .andExpect(jsonPath("$.labels[0]", is(outputLabel)));
    }

    private void updateLabel_LabelExists_ReturnsUpdatedProjectLabels() throws Exception {
        String inputContent = "{ \"title\": \"updated_test_label\", \"colorCode\": \"000000\"}";

        Map<String, Object> outputLabel = new LinkedHashMap<>();
        outputLabel.put("id", 2);
        outputLabel.put("title", "updated_test_label");
        outputLabel.put("colorCode", "000000");

        mvc.perform(patch("/api/project/1/label/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.labels").isArray())
                .andExpect(jsonPath("$.labels[1]", is(outputLabel)));
    }

    private void deleteLabel_LabelExists_ReturnsEmpty() throws Exception {
        Map<String, Object> outputLabel = new LinkedHashMap<>();
        outputLabel.put("id", 1);
        outputLabel.put("title", "test_label");
        outputLabel.put("colorCode", "FFFFFF");
        mvc.perform(delete("/api/project/1/label/2")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.labels[0]", is(outputLabel)));
    }
}
