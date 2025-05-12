package pt.up.fe.specs.socsim.emitter.template.dpi;

import pt.up.fe.specs.socsim.model.register.Register;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DpiParameterGenerator {
    public static String generateTypedArgs(List<Register> regs, boolean isRecv, boolean usePointer) {
        return regs.stream()
                .map(reg -> {
                    String base = reg.dpiType().getType();
                    if (isRecv) {
                        return (usePointer ? base + " *" : "output " + base + " ") + reg.name();
                    } else {
                        return base + " " + reg.name();
                    }
                })
                .collect(Collectors.joining(", "));
    }

    public static String generateArgList(List<Register> regs) {
        return regs.stream()
                .map(Register::name)
                .collect(Collectors.joining(", "));
    }

    public static String generateFormatString(int count) {
        return String.join(" ", Collections.nCopies(count, "%d"));
    }

    public static String generateSendParams(List<Register> registers) {
        return generateParams(registers, "int ", "");
    }

    public static String generateRegNameList(List<Register> registers) {
        return registers.stream()
                .map(Register::name)
                .collect(Collectors.joining(", "));
    }

    public static String generateDpiSendArgs(List<Register> registers) {
        return registers.stream()
                .map(reg -> reg.dpiType().getType() + " " + reg.name())
                .collect(Collectors.joining(", "));
    }

    public static String generateDpiRecvArgs(List<Register> registers) {
        return registers.stream()
                .map(reg -> "output " + reg.dpiType().getType() + " " + reg.name())
                .collect(Collectors.joining(", "));
    }

    public static String generateRecvParams(List<Register> registers) {
        return generateParams(registers, "int *", "");
    }

    public static String generateSendArgs(List<Register> registers) {
        return generateParams(registers, "", "");
    }

    public static String generateRecvZeroInit(List<Register> registers) {
        StringBuilder sb = new StringBuilder();
        for (Register register : registers) {
            sb.append("    *").append(register.name()).append("_o = 0;\n");
        }
        return sb.toString().trim();
    }

    private static String generateParams(List<Register> registers, String prefix, String suffix) {
        return registers.stream()
                .map(reg -> prefix + reg.name() + suffix)
                .collect(Collectors.joining(", "));
    }

}
