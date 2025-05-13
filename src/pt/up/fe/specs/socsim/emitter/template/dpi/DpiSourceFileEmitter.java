package pt.up.fe.specs.socsim.emitter.template.dpi;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;

public class DpiSourceFileEmitter extends TemplateEmitter {
    public DpiSourceFileEmitter(Config config) {
        super(config, "/templates/template_dpi.stg");
    }

    @Override
    protected String getTemplateName() {
        return "dpi_source";
    }
}
