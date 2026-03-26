package translation.layer.odrl_opa.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import translation.layer.odrl_opa.model.AccessRequest;
import java.util.Map;

@Service
public class OpaService {

    private final RestTemplate restTemplate;
    private final String OPA_URL = "http://localhost:8181/v1/data/auth/allow";

    public OpaService() {
        this.restTemplate = new RestTemplate();
    }

    // constructor untuk testing
    public OpaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean checkAccess(AccessRequest request) {
        Map<String, Object> input = Map.of(
                "role", request.getRole(),
                "action", request.getAction(),
                "resource", request.getResource());

        Map<String, Object> body = Map.of("input", input);

        Map<String, Object> response = restTemplate.postForObject(OPA_URL, body, Map.class);

        if (response == null || !response.containsKey("result")) {
            return false;
        }

        return (Boolean) response.get("result");
    }
}
