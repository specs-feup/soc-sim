package pt.up.fe.specs.socsim.emitter.template.verilog;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

import java.util.HashMap;
import java.util.Map;

public class VerilogInterfaceEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_sv_interface.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "interface";

    public VerilogInterfaceEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emit() {
        return null;
    }

    // TODO: review this implementation
    @Override
    public String emitToString() {
        ST template = templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        Map[] registers = module.registers().stream()
                .map(reg -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("type", reg.type().getType().equals("logic") ? "logic" : "bit");
                    data.put("widthMinusOne", reg.width() - 1);
                    data.put("name", reg.name());
                    data.put("initial", reg.initial().toString());
                    return data;
                })
                .toArray(Map[]::new);

        template.add("module", getModuleNameVariants())
                .add("registers", registers)
                .add("hasReg", module.interfaces().reg())
                .add("hasObiMaster", module.interfaces().obiMaster())
                .add("hasObiSlave", module.interfaces().obiSlave());

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
