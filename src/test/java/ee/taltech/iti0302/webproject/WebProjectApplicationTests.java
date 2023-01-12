package ee.taltech.iti0302.webproject;

import ee.taltech.iti0302.webproject.integration.AbstractIntegrationTestClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class WebProjectApplicationTests extends AbstractIntegrationTestClass {

    @Test
    @SuppressWarnings("java:S2699")
    void contextLoads() {
    }

}
