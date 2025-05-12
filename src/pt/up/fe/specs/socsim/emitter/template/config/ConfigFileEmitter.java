package pt.up.fe.specs.socsim.emitter.template.config;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class ConfigFileEmitter extends TemplateEmitter {
    private static final String TEMPLATE_FILE = "/templates/template_config.stg";
    private final String templateName;

    public ConfigFileEmitter(Module module, ConfigFileType type) {
        super(module, TEMPLATE_FILE, type.getTemplateName());

        this.templateName = type.getTemplateName();
    }

    @Override
    public String emitToString() {
        ST template = this.templates.getInstanceOf(templateName);
        if (template == null)
            throw new IllegalStateException("Template '" + templateName + "' not found in " + TEMPLATE_FILE);

        template.add("module", getModuleData());

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {
    }
}
