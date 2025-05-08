package pt.up.fe.specs.socsim.emitter.template.config;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class VltConfigFileEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_config_core.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "core_config_dpi";

    public VltConfigFileEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emitToString() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        template.add("module", getModuleNameVariants());

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
