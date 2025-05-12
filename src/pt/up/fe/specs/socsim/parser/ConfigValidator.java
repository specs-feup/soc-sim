package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigValidator {
    public static List<String> validate(String path) throws IOException {
        List<String> errors = new ArrayList<>();

        JsonReader reader = new JsonReader(path);
        JsonReader module = reader.getObject("module").orElse(null);

        if (module == null) {
            errors.add("Missing 'module' object");

            return errors;
        }

        if (!module.has("name"))
            errors.add("Missing required field: module.name");

        if (!module.has("offset"))
            errors.add("Missing required field: module.offset");

        if (!module.has("size"))
            errors.add("Missing required field: module.size");

        JsonReader interfaces = module.getObject("interfaces").orElse(null);
        if (interfaces == null)
            errors.add("Missing required field: module.interfaces");
        else
            for (String key : new String[]{"reg", "obi_master", "obi_slave"})
                if (!interfaces.has(key))
                    errors.add("Missing required field: module.interfaces." + key);

        List<JsonReader> registers = module.getArray("registers");
        if (registers.isEmpty())
            return errors;

        for (int i = 0; i < registers.size(); i++) {
            JsonReader reg = registers.get(i);
            String base = "registers[" + i + "]";

            if (!reg.has("name"))
                errors.add(base + ": missing name");

            if (!reg.has("verilog_type"))
                errors.add(base + ": missing Verilog type");

            if (!reg.has("dpi_type"))
                errors.add(base + ": missing DPI type");

            if (!reg.has("width"))
                errors.add(base + ": missing width");
        }

        return errors;
    }
}
