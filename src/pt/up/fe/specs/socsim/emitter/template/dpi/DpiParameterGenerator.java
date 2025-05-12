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
}
