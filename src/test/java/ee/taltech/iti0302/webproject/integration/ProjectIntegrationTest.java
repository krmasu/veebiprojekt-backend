package ee.taltech.iti0302.webproject.integration;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectIntegrationTest extends AbstractIntegrationTestClass {
    @Autowired
    private MockMvc mvc;

    @Test
    void testProjectEndpoints() throws Exception {
        createProject_ProjectIsCreatedCorrectly_ReturnsProjects();
        getProjectById_ProjectExists_ReturnsProjects();
        getProjectMembers_MembersExist_ReturnsMembers();
        updateProject_projectExists_ReturnsUpdatedProject();
        deleteProject_projectExists_ReturnsProjects();
    }

    private void createProject_ProjectIsCreatedCorrectly_ReturnsProjects() throws Exception {
        String inputContent = "{\"ownerId\": 1," +
                "\"title\": \"test_project2\"}";

        Map<String, Object> outputProject = new LinkedHashMap<>();
        outputProject.put("id", 1);
        outputProject.put("title", "test_project");

        Map<String, Object> outputProject2 = new LinkedHashMap<>();
        outputProject2.put("id", 2);
        outputProject2.put("title", "test_project2");

        mvc.perform(post("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]", is(outputProject)))
                .andExpect(jsonPath("$[1]", is(outputProject2)));
    }


    private void getProjectById_ProjectExists_ReturnsProjects() throws Exception {
        Map<String, Object> outputProject = new LinkedHashMap<>();
        outputProject.put("id", 1);
        outputProject.put("title", "test_project");

        mvc.perform(get("/api/project/1")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(outputProject)));
    }

    private void updateProject_projectExists_ReturnsUpdatedProject() throws Exception {
        String inputContent = "{\"projectId\": 2," +
                "\"title\": \"updated_test_project\"}";

        Map<String, Object> updatedProject = new LinkedHashMap<>();
        updatedProject.put("id", 2);
        updatedProject.put("title", "updated_test_project");

        mvc.perform(patch("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(updatedProject)));
    }

    private void deleteProject_projectExists_ReturnsProjects() throws Exception {
        String inputContent = "{\"ownerId\": 1," +
                "\"projectId\": 2}";

        Map<String, Object> outputProject = new LinkedHashMap<>();
        outputProject.put("id", 1);
        outputProject.put("title", "test_project");

        mvc.perform(delete("/api/project")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputContent)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(outputProject)));
    }

    private void getProjectMembers_MembersExist_ReturnsMembers() throws Exception {
        Map<String, Object> outputUserResponse = new LinkedHashMap<>();
        outputUserResponse.put("id", 1);
        outputUserResponse.put("username", "test_user");

        mvc.perform(get("/api/project/1/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is(outputUserResponse)));
    }
}
