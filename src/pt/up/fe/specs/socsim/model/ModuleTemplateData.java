package pt.up.fe.specs.socsim.model;

import pt.up.fe.specs.socsim.emitter.template.dpi.DpiParameterGenerator;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.model.register.TemplateRegister;

import java.util.List;
import java.util.stream.Collectors;

public class ModuleTemplateData {
    public final String name;
    public final String lowerName;
    public final String upperName;

    public final String size;
    public final String offset;

    public final boolean hasReg;
    public final boolean hasObiMaster;
    public final boolean hasObiSlave;

    public final List<Register> registers;
    public final List<TemplateRegister> templateRegisters;

    public final String dpiSendSignature;
    public final String dpiRecvSignature;
    public final String dpiSendCallArgs;
    public final String dpiRecvCallArgs;
    public final String dpiRecvPointerParams;
    public final String dpiSendFormat;
    public final String dpiRecvFormat;
    public final int registerCount;

    public ModuleTemplateData(Module module) {
        this.name = module.name();
        this.lowerName = module.name().toLowerCase();
        this.upperName = module.name().toUpperCase();
        this.size = module.size();
        this.offset = module.offset();

        this.hasReg = module.interfaces().reg();
        this.hasObiMaster = module.interfaces().obiMaster();
        this.hasObiSlave = module.interfaces().obiSlave();

        this.registers = module.registers();
        this.templateRegisters = module.registers().stream()
                .map(TemplateRegister::new)
                .collect(Collectors.toList());

        this.dpiSendSignature = DpiParameterGenerator.generateTypedArgs(registers, false, false);
        this.dpiRecvSignature = DpiParameterGenerator.generateTypedArgs(registers, true, false);
        this.dpiRecvPointerParams = DpiParameterGenerator.generateTypedArgs(registers, true, true);

        this.dpiSendCallArgs = DpiParameterGenerator.generateArgList(registers);
        this.dpiRecvCallArgs = DpiParameterGenerator.generateArgList(registers);
        this.dpiSendFormat = DpiParameterGenerator.generateFormatString(registers.size());
        this.dpiRecvFormat = DpiParameterGenerator.generateFormatString(registers.size());

        this.registerCount = registers.size();
    }
}
