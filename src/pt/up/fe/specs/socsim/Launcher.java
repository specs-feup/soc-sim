package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.dpi.instances.DpiHeaderEmitter;
import pt.up.fe.specs.socsim.emitter.dpi.instances.DpiSourceEmitter;
import pt.up.fe.specs.socsim.emitter.sv.tb.TestHarnessEmitter;
import pt.up.fe.specs.socsim.emitter.sv.tb.TestHarnessPkgEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String resource = "/config.json";

        Module module = ModuleParser.parse(resource);

        TestHarnessPkgEmitter emitter = new TestHarnessPkgEmitter("/sv/test_harness_pkg.sv", module);

        System.out.println(emitter.emit());
    }
}
