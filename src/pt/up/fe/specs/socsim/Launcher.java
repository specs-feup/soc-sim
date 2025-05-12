package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.verilog.VerilogInterfaceEmitter;
import pt.up.fe.specs.socsim.generator.Generator;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.core.CoreFileModifier;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessModifier;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessPkgModifier;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Module module = ConfigParser.parse("config/config.json");

        CoreFileModifier modifier = new CoreFileModifier(module, "/home/pedro-ramalho/x-heep/x-heep-tb-utils.core");

        System.out.println(modifier.modifyToString());
    }
}
