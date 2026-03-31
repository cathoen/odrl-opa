package translation.layer.odrl_opa;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PerformanceTest {
    private Map<String, Object> generateOdrl(int size) {
        List<Map<String, Object>> permissions = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Map<String, Object> p = new HashMap<>();
            p.put("assignee", "teacher" + i);
            p.put("action", "read");
            p.put("target", "student_grade");
            permissions.add(p);
        }

        Map<String, Object> match = new HashMap<>();
        match.put("assignee", "teacher");
        match.put("action", "read");
        match.put("target", "student_grade");

        permissions.add(match);

        Map<String, Object> odrl = new HashMap<>();
        odrl.put("permission", permissions);

        return odrl;
    }


    @Test
    void comparePerformanceAndSaveToFile() throws Exception {
        Map<String, Object> odrl = generateOdrl(10000);
        System.out.println("Generated rules: " +
                ((List<?>) odrl.get("permission")).size());

        String url = "http://localhost:8181/v1/data/auth/allow";

        Map<String, Object> input = Map.of(
                "input", Map.of(
                        "role", "teacher2321",
                        "action", "read",
                        "resource", "student_grade"));

        RestTemplate rest = new RestTemplate();

        int runs = 200;

        for (int i = 0; i < 10; i++) {
            rest.postForObject(url, input, Map.class);
        }

        long total = 0;

        StringBuilder output = new StringBuilder();
        output.append("run,duration_ms\n");

        for (int i = 0; i < runs; i++) {
            long start = System.currentTimeMillis();

            rest.postForObject(url, input, Map.class);

            long end = System.currentTimeMillis();
            long duration = end - start;

            System.out.println("Run " + (i + 1) + ": " + duration + " ms");

            output.append((i + 1)).append(",").append(duration).append("\n");

            total += duration;
        }

        long avg = total / runs;

        output.append("average,").append(avg).append("\n");

        Path filePath = Path.of("performance-result-B.txt");
        Files.writeString(filePath, output.toString());

        System.out.println("Average: " + avg + " ms");

        System.out.println("Result saved to: " + filePath.toAbsolutePath());
    }

}
