package pt.up.fe.specs.socsim.emitter.template.app;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class SwAppEmitter extends TemplateEmitter {
    public SwAppEmitter(Module module) {
        super(module, "/templates/template_sw_app.stg");
    }

    @Override
    protected String getTemplateName() {
        return "sw_app";
    }
}
