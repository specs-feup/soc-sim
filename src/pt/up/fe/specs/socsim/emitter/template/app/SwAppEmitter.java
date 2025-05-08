package pt.up.fe.specs.socsim.emitter.template.app;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class SwAppEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_sw_app.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "sw_app";

    public SwAppEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emit() {
        return null;
    }

    @Override
    public String emitToString() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        var variants = getModuleNameVariants();
        variants.put("offset", this.module.offset());
        variants.put("size", this.module.size());

        template.add("module", variants);

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
