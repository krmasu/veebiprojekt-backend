package ee.taltech.iti0302.webproject.integration;

import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskIntegrationTest extends AbstractIntegrationTestClass {
    @Autowired
    private MockMvc mvc;

    @Test
    void testTaskEndpoints() throws Exception {
        createTask_TaskIsCreatedCorrectly_ReturnsTasks();
        getTasks_TaskExists_ReturnsTasks();
        updateTask_TaskExists_ReturnsUpdatedTasks();
        deleteTask_TaskExists_ReturnsEmpty();
    }

    private void createTask_TaskIsCreatedCorrectly_ReturnsTasks() throws Exception {
        String inputContent = "{\"title\": \"test_task2\"," +
                "\"description\": \"test description 2\"," +
                "\"deadline\": \"2022-12-12\"," +
                "\"projectId\": 1," +
                "\"assigneeId\": 1," +
                "\"statusId\": 1," +
                "\"milestoneId\": 1," +
                "\"labelIds\": [1]}";

        JSONArray date1 = new JSONArray();
        date1.add(2022);
        date1.add(12);
        date1.add(22);

        JSONArray date2 = new JSONArray();
        date2.add(2022);
        date2.add(12);
        date2.add(12);

        Map<String, Object> assignee = new LinkedHashMap<>();
        assignee.put("id", 1);
        assignee.put("username", "test_user");

        Map<String, Object> label = new LinkedHashMap<>();
        label.put("id", 1);
        label.put("title", "test_label");
        label.put("colorCode", "FFFFFF");

        Map<String, Object> outputTask = new LinkedHashMap<>();
        outputTask.put("id", 1);
        outputTask.put("title", "test_task");
        outputTask.put("description", "test description");
        outputTask.put("deadline", date1);
        outputTask.put("projectId", 1);
        outputTask.put("assignee", assignee);
        outputTask.put("statusId", 1);
        outputTask.put("milestoneId", 1);
        outputTask.put("labels", new ArrayList<>(List.of(label)));

        Map<String, Object> outputTask2 = new LinkedHashMap<>();
        outputTask2.put("id", 2);
        outputTask2.put("title", "test_task2");
        outputTask2.put("description", "test description 2");
        outputTask2.put("deadline", date2);
        outputTask2.put("projectId", 1);
        outputTask2.put("assignee", assignee);
        outputTask2.put("statusId", 1);
        outputTask2.put("milestoneId", 1);
        outputTask2.put("labels", new ArrayList<>(List.of(label)));

        mvc.perform(post("/api/project/1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks[0]", is(outputTask)))
                .andExpect(jsonPath("$.tasks[1]", is(outputTask2)));
    }


    private void getTasks_TaskExists_ReturnsTasks() throws Exception {
        JSONArray date1 = new JSONArray();
        date1.add(2022);
        date1.add(12);
        date1.add(22);

        Map<String, Object> assignee = new LinkedHashMap<>();
        assignee.put("id", 1);
        assignee.put("username", "test_user");

        Map<String, Object> label = new LinkedHashMap<>();
        label.put("id", 1);
        label.put("title", "test_label");
        label.put("colorCode", "FFFFFF");

        Map<String, Object> outputTask = new LinkedHashMap<>();
        outputTask.put("id", 1);
        outputTask.put("title", "test_task");
        outputTask.put("description", "test description");
        outputTask.put("deadline", date1);
        outputTask.put("projectId", 1);
        outputTask.put("assignee", assignee);
        outputTask.put("statusId", 1);
        outputTask.put("milestoneId", 1);
        outputTask.put("labels", new ArrayList<>(List.of(label)));

        mvc.perform(get("/api/project/1/task")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks[0]", is(outputTask)));
    }

    private void updateTask_TaskExists_ReturnsUpdatedTasks() throws Exception {
        String inputContent = "{\"title\": \"updated_test_task\"}";

        JSONArray date2 = new JSONArray();
        date2.add(2022);
        date2.add(12);
        date2.add(12);

        Map<String, Object> assignee = new LinkedHashMap<>();
        assignee.put("id", 1);
        assignee.put("username", "test_user");

        Map<String, Object> label = new LinkedHashMap<>();
        label.put("id", 1);
        label.put("title", "test_label");
        label.put("colorCode", "FFFFFF");

        Map<String, Object> outputTask2 = new LinkedHashMap<>();
        outputTask2.put("id", 2);
        outputTask2.put("title", "updated_test_task");
        outputTask2.put("description", "test description 2");
        outputTask2.put("deadline", date2);
        outputTask2.put("projectId", 1);
        outputTask2.put("assignee", assignee);
        outputTask2.put("statusId", 1);
        outputTask2.put("milestoneId", 1);
        outputTask2.put("labels", new ArrayList<>(List.of(label)));

        mvc.perform(patch("/api/project/1/task/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.tasks").isArray())
                .andExpect(jsonPath("$.tasks[1]", is(outputTask2)));
    }

    private void deleteTask_TaskExists_ReturnsEmpty() throws Exception {
        JSONArray date1 = new JSONArray();
        date1.add(2022);
        date1.add(12);
        date1.add(22);

        Map<String, Object> assignee = new LinkedHashMap<>();
        assignee.put("id", 1);
        assignee.put("username", "test_user");

        Map<String, Object> label = new LinkedHashMap<>();
        label.put("id", 1);
        label.put("title", "test_label");
        label.put("colorCode", "FFFFFF");

        Map<String, Object> outputTask = new LinkedHashMap<>();
        outputTask.put("id", 1);
        outputTask.put("title", "test_task");
        outputTask.put("description", "test description");
        outputTask.put("deadline", date1);
        outputTask.put("projectId", 1);
        outputTask.put("assignee", assignee);
        outputTask.put("statusId", 1);
        outputTask.put("milestoneId", 1);
        outputTask.put("labels", new ArrayList<>(List.of(label)));
        mvc.perform(delete("/api/project/1/task/2")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(20))
                .andExpect(jsonPath("$.tasks[0]", is(outputTask)));
    }
}
