package translation.layer.odrl_opa.service;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class OdrlToRegoServiceTest {

    private final OdrlToRegoService service = new OdrlToRegoService();

    @Test
    void testConvertPermission() {
        Map<String, Object> odrl = new HashMap<>();

        List<Map<String, String>> permissions = new ArrayList<>();
        Map<String, String> p = new HashMap<>();
        p.put("assignee", "teacher");
        p.put("action", "read");
        p.put("target", "student_grade");

        permissions.add(p);
        odrl.put("permission", permissions);

        String result = service.convert(odrl);

        assertTrue(result.contains("input.role == \"teacher\""));
        assertTrue(result.contains("input.action == \"read\""));
        assertTrue(result.contains("input.resource == \"student_grade\""));
        assertTrue(result.contains("allow if"));
    }

    @Test
    void testConvertProhibition() {
        Map<String, Object> odrl = new HashMap<>();

        List<Map<String, String>> prohibitions = new ArrayList<>();
        Map<String, String> p = new HashMap<>();
        p.put("assignee", "guest");
        p.put("action", "read");
        p.put("target", "student_grade");

        prohibitions.add(p);
        odrl.put("prohibition", prohibitions);

        String result = service.convert(odrl);

        assertTrue(result.contains("deny if"));
        assertTrue(result.contains("input.role == \"guest\""));
    }
}
