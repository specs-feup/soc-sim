package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.app.SwAppEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.DpiCoreFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.VerilogCoreFileEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessModifier;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessPkgModifier;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String resource = "/config/config.json";

        Module module = ModuleParser.parse(resource);

        TestHarnessModifier modifier = new TestHarnessModifier(module, "data/test_harness.sv");

        System.out.println(modifier.modifyToString());
    }
}
