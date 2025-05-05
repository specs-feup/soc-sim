package pt.up.fe.specs.socsim.emitter.sv;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.register.Register;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InterfaceEmitter implements Emitter {
    private static final String TEMPLATE_FILE = "/interface.stg";
    private static final String TEMPLATE_NAME = "interface";

    private final Module module;
    private final STGroup templates;

    public InterfaceEmitter(Module module) {
        this.module = validate(Objects.requireNonNull(module, "Module cannot be null"));
        this.templates = load();
    }

    private Module validate(Module module) {
        if (module.name() == null || module.name().trim().isEmpty()) {
            throw new IllegalArgumentException("Module name cannot be empty");
        }
        return module;
    }

    private STGroup load() {
        try {
            InputStream in = getClass().getResourceAsStream(TEMPLATE_FILE);
            if (in == null) {
                throw new IllegalStateException("Template file not found: " + TEMPLATE_FILE);
            }

            STGroup group = new STGroupString(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            group.load();

            return group;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load templates from " + TEMPLATE_FILE, e);
        }
    }

    @Override
    public String emit() {
        System.out.println("Module name: " + module.name());

        ST template = templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        Map[] registers = module.registers().stream()
                .map(reg -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("type", reg.type().getType().equals("logic") ? "logic" : "bit");
                    data.put("widthMinusOne", reg.width() - 1);  // Pre-calculate width-1
                    data.put("name", reg.name());
                    data.put("initial", reg.initial().toString());
                    return data;
                })
                .toArray(Map[]::new);

        Map<String, Object> moduleData = new HashMap<>();
        moduleData.put("name", this.module.name());

        template.add("module", moduleData)
                .add("registers", registers)
                .add("hasReg", module.interfaces().reg())
                .add("hasObiMaster", module.interfaces().obiMaster())
                .add("hasObiSlave", module.interfaces().obiSlave())
                .add("dpiSend", module.dpi().send())
                .add("dpiRecv", module.dpi().recv());

        return template.render();
    }
}