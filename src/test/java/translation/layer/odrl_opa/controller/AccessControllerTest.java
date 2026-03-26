package translation.layer.odrl_opa.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import translation.layer.odrl_opa.model.AccessRequest;
import translation.layer.odrl_opa.service.OpaService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AccessControllerTest {

    private OpaService opaService;
    private AccessController accessController;

    @BeforeEach
    void setUp() {
        opaService = mock(OpaService.class);
        accessController = new AccessController(opaService);
    }

    @Test
    void access_shouldReturnALLOW_whenOpaServiceReturnsTrue() {
        // given
        AccessRequest request = new AccessRequest();
        request.setRole("teacher");
        request.setAction("read");
        request.setResource("student_grade");

        when(opaService.checkAccess(request)).thenReturn(true);

        // when
        String result = accessController.access(request);

        // then
        assertThat(result).isEqualTo("ALLOW");
        verify(opaService, times(1)).checkAccess(request);
    }

    @Test
    void access_shouldReturnDENY_whenOpaServiceReturnsFalse() {
        AccessRequest request = new AccessRequest();
        request.setRole("guest");
        request.setAction("read");
        request.setResource("student_grade");

        when(opaService.checkAccess(request)).thenReturn(false);

        String result = accessController.access(request);

        assertThat(result).isEqualTo("DENY");
        verify(opaService, times(1)).checkAccess(request);
    }
}