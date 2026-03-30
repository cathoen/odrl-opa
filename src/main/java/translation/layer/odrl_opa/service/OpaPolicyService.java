package translation.layer.odrl_opa.service;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

@Service
public class OpaPolicyService {

    private final RestTemplate restTemplate;
    private final String OPA_POLICY_URL = "http://localhost:8181/v1/policies/auth";

    public OpaPolicyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void uploadPolicy(String rego) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        HttpEntity<String> request = new HttpEntity<>(rego, headers);

        restTemplate.exchange(
                OPA_POLICY_URL,
                HttpMethod.PUT,
                request,
                String.class
        );
    }
}
