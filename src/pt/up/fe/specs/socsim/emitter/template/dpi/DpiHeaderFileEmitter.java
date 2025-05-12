package pt.up.fe.specs.socsim.emitter.template.dpi;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class DpiHeaderFileEmitter extends TemplateEmitter {
    public DpiHeaderFileEmitter(Module module) {
        super(module, "/templates/template_dpi.stg");
    }

    @Override
    protected String getTemplateName() {
        return "dpi_header";
    }
}
