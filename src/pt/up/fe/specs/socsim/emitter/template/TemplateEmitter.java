package pt.up.fe.specs.socsim.emitter.template;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.emitter.template.dpi.DpiParameterGenerator;
import pt.up.fe.specs.socsim.model.Module;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class TemplateEmitter implements Emitter {
    protected static String TEMPLATE_FILE;
    protected static String TEMPLATE_NAME;

    protected final Module module;
    protected final STGroup templates;

    protected TemplateEmitter(Module module, String templateFile, String templateName) {
        TEMPLATE_FILE = templateFile;
        TEMPLATE_NAME = templateName;

        this.module = module;
        this.templates = this.load();
    }

    protected STGroup load() {
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

    protected Map<String, String> getModuleData() {
        Map<String, String> data = new HashMap<>();

        data.put("name", module.name());
        data.put("lowerName", module.name().toLowerCase());
        data.put("upperName", module.name().toUpperCase());

        data.put("regNameList", DpiParameterGenerator.generateRegNameList(module.registers()));
        data.put("dpiSendArgs", DpiParameterGenerator.generateDpiSendArgs(module.registers()));
        data.put("dpiRecvArgs", DpiParameterGenerator.generateDpiRecvArgs(module.registers()));


        return data;
    }
}
