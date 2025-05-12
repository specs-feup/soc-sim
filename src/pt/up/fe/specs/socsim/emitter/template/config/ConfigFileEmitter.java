package pt.up.fe.specs.socsim.emitter.template.config;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class ConfigFileEmitter extends TemplateEmitter {
    private final String templateName;

    public ConfigFileEmitter(Module module, ConfigFileType type) {
        super(module, "/templates/template_config.stg");

        this.templateName = type.getTemplateName();
    }

    @Override
    protected String getTemplateName() {
        return templateName;
    }
}
