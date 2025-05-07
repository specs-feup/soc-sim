package pt.up.fe.specs.socsim.emitter.dpi.instances;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.emitter.dpi.BaseDpiEmitter;
import pt.up.fe.specs.socsim.emitter.dpi.DpiParameterGenerator;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.register.Register;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DpiHeaderEmitter extends BaseDpiEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/dpi_header.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "header";

    public DpiHeaderEmitter(Module module) {
        super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME);
    }

    @Override
    public String emit() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        template.add("moduleName", module.name())
                .add("moduleLower", module.name().toLowerCase())
                .add("moduleUpper", module.name().toUpperCase())
                .add("sendParams", DpiParameterGenerator.generateSendParams(module.registers()))
                .add("recvParams", DpiParameterGenerator.generateRecvParams(module.registers()));

        return template.render();
    }
}
