package pt.up.fe.specs.socsim.emitter.dpi;

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

public class DpiHeaderEmitter implements Emitter {
    private static final String TEMPLATE_FILE = "/dpi_header.stg";
    private static final String TEMPLATE_NAME = "header";

    private final Module module;
    private final STGroup templates;

    public DpiHeaderEmitter(Module module) {
        this.module = module;
        this.templates = this.load();
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

    private String generateSendParams() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Register register : this.module.registers()) {
            if (!first) sb.append(", ");

            sb.append("int ").append(register.name());

            first = false;
        }

        return sb.toString();
    }

    private String generateRecvParams() {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (Register register : this.module.registers()) {
            if (!first) sb.append(", ");

            sb.append("int *").append(register.name());

            first = false;
        }

        return sb.toString();
    }

    @Override
    public String emit() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        template.add("moduleName", module.name());
        template.add("moduleLower", module.name().toLowerCase());
        template.add("moduleUpper", module.name().toUpperCase());
        template.add("sendParams", this.generateSendParams());
        template.add("recvParams", this.generateRecvParams());

        return template.render();
    }
}
