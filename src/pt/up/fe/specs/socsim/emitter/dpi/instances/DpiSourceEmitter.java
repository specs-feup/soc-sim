package pt.up.fe.specs.socsim.emitter.dpi.instances;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.emitter.dpi.BaseDpiEmitter;
import pt.up.fe.specs.socsim.emitter.dpi.DpiParameterGenerator;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.register.Register;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DpiSourceEmitter extends BaseDpiEmitter {
    private static final String DEFAULT_TEMPLATE_FILE = "/dpi_source.stg";
    private static final String DEFAULT_TEMPLATE_NAME = "source";

    public DpiSourceEmitter(Module module) {
        super(module, DEFAULT_TEMPLATE_FILE, DEFAULT_TEMPLATE_NAME);
    }

    @Override
    public String emit() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null) {
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);
        }

        List<Register> registers = module.registers();
        int regCount = registers.size();

        Map<String, Object> dpiParams = new HashMap<>();
        dpiParams.put("sendParams", DpiParameterGenerator.generateSendParams(registers));
        dpiParams.put("recvParams", DpiParameterGenerator.generateRecvParams(registers));
        dpiParams.put("sendFormat", DpiParameterGenerator.generateFormatString(regCount));
        dpiParams.put("recvFormat", DpiParameterGenerator.generateFormatString(regCount));
        dpiParams.put("sendArgs", DpiParameterGenerator.generateSendArgs(registers));
        dpiParams.put("recvArgs", DpiParameterGenerator.generateRecvArgs(registers));
        dpiParams.put("recvZeroInit", DpiParameterGenerator.generateRecvZeroInit(registers));
        dpiParams.put("numRegs", regCount);

        template.add("module", getModuleNameVariants())
                .add("dpi", dpiParams);

        return template.render();
    }
}
