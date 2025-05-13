package pt.up.fe.specs.socsim.emitter.template.app;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;

public class SwAppEmitter extends TemplateEmitter {
    public SwAppEmitter(Config config) {
        super(config, "/templates/template_sw_app.stg");
    }

    @Override
    protected String getTemplateName() {
        return "sw_app";
    }
}
