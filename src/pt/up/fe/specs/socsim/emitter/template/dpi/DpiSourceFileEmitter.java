package pt.up.fe.specs.socsim.emitter.template.dpi;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.ModuleTemplateData;
import pt.up.fe.specs.socsim.model.register.Register;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DpiSourceFileEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_dpi.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "dpi_source";

    public DpiSourceFileEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emitToString() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        template.add("module", new ModuleTemplateData(this.module));

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
