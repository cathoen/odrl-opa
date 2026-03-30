package translation.layer.odrl_opa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import translation.layer.odrl_opa.service.OdrlToRegoService;
import translation.layer.odrl_opa.service.OpaPolicyService;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CompileControllerTest {

    @Test
    void testCompileAndUpload() throws Exception {
        OdrlToRegoService converter = mock(OdrlToRegoService.class);
        OpaPolicyService service = mock(OpaPolicyService.class);

        when(converter.convert(any())).thenReturn("package auth");

        CompileController controller = new CompileController(converter, service);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Map<String, Object> odrl = new HashMap<>();

        mockMvc.perform(post("/compile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(odrl)))
                .andExpect(status().isOk())
                .andExpect(content().string("package auth"));

        verify(service, times(1)).uploadPolicy("package auth");
    }
}