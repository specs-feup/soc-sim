package pt.up.fe.specs.socsim.emitter.template.dpi;

import org.stringtemplate.v4.ST;
import pt.up.fe.specs.socsim.emitter.template.TemplateEmitter;
import pt.up.fe.specs.socsim.model.Module;

public class DpiHeaderFileEmitter extends TemplateEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/templates/template_dpi.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "dpi_header";

    public DpiHeaderFileEmitter(Module module) { super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME); }

    @Override
    public String emitToString() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        template.add("module", getModuleData());
        template.add("sendParams", DpiParameterGenerator.generateSendParams(module.registers()))
                .add("recvParams", DpiParameterGenerator.generateRecvParams(module.registers()));

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
