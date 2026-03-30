package translation.layer.odrl_opa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void testFullFlow_allow() {
        String baseUrl = "http://localhost:" + port;

        // 1. Compile policy
        Map<String, Object> odrl = new HashMap<>();
        List<Map<String, String>> permissions = new ArrayList<>();

        Map<String, String> p = new HashMap<>();
        p.put("assignee", "teacher");
        p.put("action", "read");
        p.put("target", "student_grade");

        permissions.add(p);
        odrl.put("permission", permissions);

        String compileResponse = restTemplate.postForObject(
                baseUrl + "/compile",
                odrl,
                String.class
        );

        assertNotNull(compileResponse);

        // 2. Test access
        Map<String, Object> request = new HashMap<>();
        request.put("role", "teacher");
        request.put("action", "read");
        request.put("resource", "student_grade");

        String accessResponse = restTemplate.postForObject(
                baseUrl + "/access",
                request,
                String.class
        );

        assertEquals("ALLOW", accessResponse);
    }

    @Test
    void testFullFlow_deny() {
        String baseUrl = "http://localhost:" + port;

        Map<String, Object> request = new HashMap<>();
        request.put("role", "guest");
        request.put("action", "read");
        request.put("resource", "student_grade");

        String accessResponse = restTemplate.postForObject(
                baseUrl + "/access",
                request,
                String.class
        );

        assertEquals("DENY", accessResponse);
    }
}
