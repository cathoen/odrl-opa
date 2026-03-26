package translation.layer.odrl_opa.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;
import translation.layer.odrl_opa.model.AccessRequest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class OpaServiceTest {

    private RestTemplate restTemplate;
    private OpaService opaService;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        opaService = new OpaService(restTemplate); // ubah OpaService supaya bisa inject RestTemplate
    }

    @Test
    void checkAccess_allow() {
        // given
        AccessRequest request = new AccessRequest();
        request.setRole("teacher");
        request.setAction("read");
        request.setResource("student_grade");

        Map<String, Object> mockResponse = Map.of("result", true);

        when(restTemplate.postForObject(anyString(), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        boolean result = opaService.checkAccess(request);

        assertThat(result).isTrue();

        ArgumentCaptor<Map> captor = ArgumentCaptor.forClass(Map.class);
        verify(restTemplate).postForObject(anyString(), captor.capture(), eq(Map.class));

        Map sentBody = captor.getValue();
        assertThat(sentBody).containsKey("input");
        Map input = (Map) sentBody.get("input");
        assertThat(input).containsEntry("role", "teacher")
                         .containsEntry("action", "read")
                         .containsEntry("resource", "student_grade");
    }

    @Test
    void checkAccess_deny() {
        AccessRequest request = new AccessRequest();
        request.setRole("guest");
        request.setAction("read");
        request.setResource("student_grade");

        Map<String, Object> mockResponse = Map.of("result", false);
        when(restTemplate.postForObject(anyString(), any(), eq(Map.class)))
                .thenReturn(mockResponse);

        boolean result = opaService.checkAccess(request);
        assertThat(result).isFalse();
    }

}