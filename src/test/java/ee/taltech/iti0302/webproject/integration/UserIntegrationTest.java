package ee.taltech.iti0302.webproject.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest extends AbstractIntegrationTestClass {
    @Autowired
    private MockMvc mvc;

    @Test
    void getUserData_UserExists_ReturnsUserData() throws Exception {
        mvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1}")
                        .with(user("test_user")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("test_user"))
                .andExpect(jsonPath("$.email").value("test.user@mail.com"))
                .andExpect(jsonPath("$.projects").value(new ArrayList<>()));
    }
}
