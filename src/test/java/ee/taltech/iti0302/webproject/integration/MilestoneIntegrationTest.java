package ee.taltech.iti0302.webproject.integration;

import net.minidev.json.JSONArray;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MilestoneIntegrationTest extends AbstractIntegrationTestClass {

    @Autowired
    private MockMvc mvc;

    @Test
    void testLabelEndpoints() throws Exception {
        createMilestone_MilestoneIsCreatedCorrectly_ReturnsProjectMilestones();
        getMilestone_MilestoneExists_ReturnsProjectMilestones();
        updateMilestone_MilestoneExists_ReturnsUpdatedProjectMilestones();
        deleteMilestone_MilestoneExists_ReturnsEmpty();
    }

    private void createMilestone_MilestoneIsCreatedCorrectly_ReturnsProjectMilestones() throws Exception {
        String inputContent = "{ \"title\": \"test_milestone_new\", \"description\": \"test description new\", " +
                "\"startDate\": \"2022-12-12\", \"endDate\": \"2022-12-13\"}";

        Map<String, Object> outputMilestone = new LinkedHashMap<>();
        outputMilestone.put("id", 1);
        outputMilestone.put("title", "test_milestone");
        outputMilestone.put("description", "test description");
        JSONArray startDate = new JSONArray();
        startDate.add(2022);
        startDate.add(12);
        startDate.add(22);
        outputMilestone.put("startDate", startDate);
        JSONArray endDate = new JSONArray();
        endDate.add(2022);
        endDate.add(12);
        endDate.add(23);
        outputMilestone.put("endDate", endDate);

        Map<String, Object> outputMilestoneNew = new LinkedHashMap<>();
        outputMilestoneNew.put("id", 2);
        outputMilestoneNew.put("title", "test_milestone_new");
        outputMilestoneNew.put("description", "test description new");
        JSONArray startDateNew = new JSONArray();
        startDateNew.add(2022);
        startDateNew.add(12);
        startDateNew.add(12);
        outputMilestoneNew.put("startDate", startDateNew);
        JSONArray endDateNew = new JSONArray();
        endDateNew.add(2022);
        endDateNew.add(12);
        endDateNew.add(13);
        outputMilestoneNew.put("endDate", endDateNew);

        mvc.perform(post("/api/project/1/milestone")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.milestones").isArray())
                .andExpect(jsonPath("$.milestones[0]", is(outputMilestone)))
                .andExpect(jsonPath("$.milestones[1]", is(outputMilestoneNew)));

    }


    private void getMilestone_MilestoneExists_ReturnsProjectMilestones() throws Exception {
        Map<String, Object> outputMilestone = new LinkedHashMap<>();
        outputMilestone.put("id", 1);
        outputMilestone.put("title", "test_milestone");
        outputMilestone.put("description", "test description");
        JSONArray startDate = new JSONArray();
        startDate.add(2022);
        startDate.add(12);
        startDate.add(22);
        outputMilestone.put("startDate", startDate);
        JSONArray endDate = new JSONArray();
        endDate.add(2022);
        endDate.add(12);
        endDate.add(23);
        outputMilestone.put("endDate", endDate);

        mvc.perform(get("/api/project/1/milestone")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.milestones").isArray())
                .andExpect(jsonPath("$.milestones[0]", is(outputMilestone)));
    }

    private void updateMilestone_MilestoneExists_ReturnsUpdatedProjectMilestones() throws Exception {
        String inputContent = "{ \"title\": \"test_milestone_updated\", \"description\": \"test description updated\", " +
                "\"startDate\": \"2023-12-12\", \"endDate\": \"2023-12-13\"}";

        Map<String, Object> outputMilestoneUpdated = new LinkedHashMap<>();
        outputMilestoneUpdated.put("id", 2);
        outputMilestoneUpdated.put("title", "test_milestone_updated");
        outputMilestoneUpdated.put("description", "test description updated");
        JSONArray startDateNew = new JSONArray();
        startDateNew.add(2023);
        startDateNew.add(12);
        startDateNew.add(12);
        outputMilestoneUpdated.put("startDate", startDateNew);
        JSONArray endDateNew = new JSONArray();
        endDateNew.add(2023);
        endDateNew.add(12);
        endDateNew.add(13);
        outputMilestoneUpdated.put("endDate", endDateNew);

        mvc.perform(patch("/api/project/1/milestone/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.milestones").isArray())
                .andExpect(jsonPath("$.milestones[1]", is(outputMilestoneUpdated)));
    }

    private void deleteMilestone_MilestoneExists_ReturnsEmpty() throws Exception {
        Map<String, Object> outputMilestone = new LinkedHashMap<>();
        outputMilestone.put("id", 1);
        outputMilestone.put("title", "test_milestone");
        outputMilestone.put("description", "test description");
        JSONArray startDate = new JSONArray();
        startDate.add(2022);
        startDate.add(12);
        startDate.add(22);
        outputMilestone.put("startDate", startDate);
        JSONArray endDate = new JSONArray();
        endDate.add(2022);
        endDate.add(12);
        endDate.add(23);
        outputMilestone.put("endDate", endDate);

        mvc.perform(delete("/api/project/1/milestone/2")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.milestones").isArray())
                .andExpect(jsonPath("$.milestones[0]", is(outputMilestone)));
    }
}
