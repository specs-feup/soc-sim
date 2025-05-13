package pt.up.fe.specs.socsim.emitter.template.config;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;

public class ConfigFileEmitter extends TemplateEmitter {
    private final String templateName;

    public ConfigFileEmitter(Config config, ConfigFileType type) {
        super(config, "/templates/template_config.stg");

        this.templateName = type.getTemplateName();
    }

    @Override
    protected String getTemplateName() {
        return templateName;
    }
}
