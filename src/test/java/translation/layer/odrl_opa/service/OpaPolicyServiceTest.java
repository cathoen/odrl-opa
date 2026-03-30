package translation.layer.odrl_opa.service;

import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;

class OpaPolicyServiceTest {

    @Test
    void testUploadPolicy() {
        RestTemplate restTemplate = mock(RestTemplate.class);

        OpaPolicyService service = new OpaPolicyService(restTemplate);

        String rego = "package auth";

        service.uploadPolicy(rego);

        verify(restTemplate, times(1)).exchange(
                eq("http://localhost:8181/v1/policies/auth"),
                eq(HttpMethod.PUT),
                any(HttpEntity.class),
                eq(String.class)
        );
    }
}