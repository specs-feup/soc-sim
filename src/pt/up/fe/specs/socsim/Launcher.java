package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.app.SwAppEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileType;
import pt.up.fe.specs.socsim.emitter.template.dpi.DpiHeaderFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.dpi.DpiSourceFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.verilog.VerilogInterfaceEmitter;
import pt.up.fe.specs.socsim.generator.Generator;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.parser.ConfigParser;
import pt.up.fe.specs.socsim.parser.ConfigValidator;

import java.io.IOException;
import java.util.List;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String config = "config/config.json";

        Generator generator = new Generator(config);
        generator.undo();
    }
}
