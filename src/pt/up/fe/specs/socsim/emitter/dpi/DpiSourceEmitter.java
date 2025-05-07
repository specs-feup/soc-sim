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
import java.util.List;
import java.util.Map;

public class DpiSourceEmitter implements Emitter {
    private static final String TEMPLATE_FILE = "/dpi_source.stg";
    private static final String TEMPLATE_NAME = "source";

    private final Module module;
    private final STGroup templates;

    public DpiSourceEmitter(Module module) {
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


    private Map<String, Object> buildDpiParams() {
        List<Register> registers = this.module.registers();

        StringBuilder sendParams = new StringBuilder();
        StringBuilder recvParams = new StringBuilder();
        StringBuilder sendFormat = new StringBuilder();
        StringBuilder recvFormat = new StringBuilder();
        StringBuilder sendArgs = new StringBuilder();
        StringBuilder recvArgs = new StringBuilder();
        StringBuilder recvZeroInit = new StringBuilder();

        int count = 0;
        for (Register register : registers) {
            String name = register.name();

            if (count > 0) {
                sendParams.append(", ");
                recvParams.append(", ");
                sendFormat.append(" ");
                recvFormat.append(" ");
                sendArgs.append(", ");
                recvArgs.append(", ");
            }

            sendParams.append("int ").append(name);
            recvParams.append("int *").append(name);

            sendFormat.append("%d");
            recvFormat.append("%d");

            sendArgs.append(name);
            recvArgs.append(name);

            recvZeroInit.append("    *").append(name).append("_o").append(" = 0;\n");

            count++;
        }

        Map<String, Object> dpi = new HashMap<>();
        dpi.put("sendParams", sendParams.toString());
        dpi.put("recvParams", recvParams.toString());
        dpi.put("sendFormat", sendFormat.toString());
        dpi.put("recvFormat", recvFormat.toString());
        dpi.put("sendPrint", sendFormat.toString());
        dpi.put("sendArgs", sendArgs.toString());
        dpi.put("recvArgs", recvArgs.toString());
        dpi.put("recvZeroInit", recvZeroInit.toString().trim());
        dpi.put("numRegs", count);

        return dpi;
    }

    @Override
    public String emit() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        Map<String, Object> moduleData = new HashMap<>();
        moduleData.put("name", module.name());
        moduleData.put("lowerName", module.name().toLowerCase());
        moduleData.put("upperName", module.name().toUpperCase());

        template.add("module", moduleData)
                .add("dpi", buildDpiParams());

        return template.render();
    }
}
