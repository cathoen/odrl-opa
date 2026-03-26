package translation.layer.odrl_opa.controller;

import org.springframework.web.bind.annotation.*;
import translation.layer.odrl_opa.model.AccessRequest;
import translation.layer.odrl_opa.service.OpaService;

@RestController
@RequestMapping("/access")
public class AccessController {

    private final OpaService opaService;

    public AccessController(OpaService opaService) {
        this.opaService = opaService;
    }

    @PostMapping
    public String access(@RequestBody AccessRequest request) {
        boolean allowed = opaService.checkAccess(request);

        return allowed ? "ALLOW" : "DENY";
    }
}