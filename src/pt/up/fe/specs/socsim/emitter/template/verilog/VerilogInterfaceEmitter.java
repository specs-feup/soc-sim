package pt.up.fe.specs.socsim.emitter.template.verilog;

import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class VerilogInterfaceEmitter extends TemplateEmitter {
    public VerilogInterfaceEmitter(Module module) {
        super(module, "/templates/template_sv_interface.stg");
    }

    @Override
    protected String getTemplateName() {
        return "sv_interface";
    }
}
