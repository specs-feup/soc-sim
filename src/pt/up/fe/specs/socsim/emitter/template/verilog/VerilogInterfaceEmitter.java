package pt.up.fe.specs.socsim.emitter.template.verilog;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.ModuleTemplateData;

import java.util.HashMap;
import java.util.Map;

public class VerilogInterfaceEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_sv_interface.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "sv_interface";

    public VerilogInterfaceEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emitToString() {
        ST template = templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        template.add("module", new ModuleTemplateData(this.module));

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
