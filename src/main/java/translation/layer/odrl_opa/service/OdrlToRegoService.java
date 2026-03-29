package translation.layer.odrl_opa.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OdrlToRegoService {

    public String convert(Map<String, Object> odrl) {
        StringBuilder rego = new StringBuilder();

        rego.append("package auth\n\n");
        rego.append("default allow = false\n\n");

        // prohibition (deny)
        List<Map<String, String>> prohibitions =
                (List<Map<String, String>>) odrl.get("prohibition");

        if (prohibitions != null) {
            for (Map<String, String> p : prohibitions) {
                rego.append("deny if {\n");
                rego.append("  input.role == \"" + p.get("assignee") + "\"\n");
                rego.append("  input.action == \"" + p.get("action") + "\"\n");
                rego.append("  input.resource == \"" + p.get("target") + "\"\n");
                rego.append("}\n\n");
            }
        }

        // permission (allow)
        List<Map<String, String>> permissions =
                (List<Map<String, String>>) odrl.get("permission");

        if (permissions != null) {
            for (Map<String, String> p : permissions) {
                rego.append("allow if {\n");
                rego.append("  not deny\n");
                rego.append("  input.role == \"" + p.get("assignee") + "\"\n");
                rego.append("  input.action == \"" + p.get("action") + "\"\n");
                rego.append("  input.resource == \"" + p.get("target") + "\"\n");
                rego.append("}\n\n");
            }
        }

        return rego.toString();
    }
}