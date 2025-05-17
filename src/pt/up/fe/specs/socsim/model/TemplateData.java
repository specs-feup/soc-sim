package pt.up.fe.specs.socsim.model;

import pt.up.fe.specs.socsim.emitter.template.dpi.DpiParameterGenerator;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.model.config.Paths;
import pt.up.fe.specs.socsim.model.config.SocketOptions;
import pt.up.fe.specs.socsim.model.config.communication.Communication;
import pt.up.fe.specs.socsim.model.config.endpoint.Endpoint;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.model.register.TemplateRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TemplateData {
    public final Paths paths;
    public final Communication communication;

    public final Endpoint e1;
    public final Endpoint e2;

    public final Integer e1SendTimeout;
    public final Integer e1RecvTimeout;
    public final Integer e1Retries;
    public final Integer e2SendTimeout;
    public final Integer e2RecvTimeout;
    public final Integer e2Retries;

    public final String e1Address;
    public final String e2Address;

    public final SocketOptions opt1;
    public final SocketOptions opt2;

    public final Module module;

    public final String name;
    public final String moduleLowerName;
    public final String moduleUpperName;

    public final Integer moduleSize;
    public final Integer moduleOffset;

    public final boolean moduleHasReg;
    public final boolean moduleHasObiMaster;
    public final boolean moduleHasObiSlave;

    public final List<Register> registers;
    public final List<TemplateRegister> moduleTemplateRegisters;

    public final String dpiSendSignature;
    public final String dpiRecvSignature;
    public final String dpiSendCallArgs;
    public final String dpiRecvCallArgs;
    public final String dpiRecvPointerParams;
    public final String dpiSendFormat;
    public final String dpiRecvFormat;
    public final int moduleRegisterCount;

    public TemplateData(Config config) {
        this.paths = config.paths();
        this.communication = config.communication();
        this.module = config.module();

        this.e1 = config.communication().e1();
        this.e2 = config.communication().e2();

        this.e1Address = this.e1.address();
        this.e2Address = this.e2.address();

        this.opt1 = config.communication().e1().options();
        this.opt2 = config.communication().e2().options();

        this.e1SendTimeout = this.opt1.sendTimeout();
        this.e1RecvTimeout = this.opt1.recvTimeout();
        this.e1Retries = this.opt1.retries();

        this.e2SendTimeout = this.opt1.sendTimeout();
        this.e2RecvTimeout = this.opt1.recvTimeout();
        this.e2Retries = this.opt1.retries();

        this.name = module.name();
        this.moduleLowerName = module.name().toLowerCase();
        this.moduleUpperName = module.name().toUpperCase();
        this.moduleSize = module.size();
        this.moduleOffset = module.offset();

        this.moduleHasReg = module.interfaces().reg();
        this.moduleHasObiMaster = module.interfaces().obiMaster();
        this.moduleHasObiSlave = module.interfaces().obiSlave();

        this.registers = module.registers();
        this.moduleTemplateRegisters = module.registers().stream()
                .map(TemplateRegister::new)
                .collect(Collectors.toList());

        this.dpiSendSignature = DpiParameterGenerator.generateTypedArgs(registers, false, false);
        this.dpiRecvSignature = DpiParameterGenerator.generateTypedArgs(registers, true, false);
        this.dpiRecvPointerParams = DpiParameterGenerator.generateTypedArgs(registers, true, true);

        this.dpiSendCallArgs = DpiParameterGenerator.generateArgList(registers);
        this.dpiRecvCallArgs = DpiParameterGenerator.generateArgList(registers);
        this.dpiSendFormat = DpiParameterGenerator.generateFormatString(registers.size());
        this.dpiRecvFormat = DpiParameterGenerator.generateFormatString(registers.size());

        this.moduleRegisterCount = registers.size();
    }
}
