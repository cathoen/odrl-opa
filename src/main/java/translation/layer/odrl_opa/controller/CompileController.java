package translation.layer.odrl_opa.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import translation.layer.odrl_opa.service.OdrlToRegoService;

@RestController
@RequestMapping("/compile")
public class CompileController {

    private final OdrlToRegoService service;

    public CompileController(OdrlToRegoService service) {
        this.service = service;
    }

    @PostMapping
    public String compile(@RequestBody Map<String, Object> odrl) {
        return service.convert(odrl);
    }
}
