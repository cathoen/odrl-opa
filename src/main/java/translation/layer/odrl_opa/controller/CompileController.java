package translation.layer.odrl_opa.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import translation.layer.odrl_opa.service.OdrlToRegoService;
import translation.layer.odrl_opa.service.OpaPolicyService;

@RestController
@RequestMapping("/compile")
public class CompileController {

    private final OdrlToRegoService converter;
    private final OpaPolicyService service;

    public CompileController(OdrlToRegoService converter, OpaPolicyService service) {
        this.converter = converter;
        this.service = service;
    }

    @PostMapping
    public String compileAndUpload(@RequestBody Map<String, Object> odrl) {
        String rego = converter.convert(odrl);
        service.uploadPolicy(rego);
        return rego;
    }
}
