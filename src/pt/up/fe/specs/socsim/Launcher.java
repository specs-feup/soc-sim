package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileType;
import pt.up.fe.specs.socsim.emitter.template.verilog.VerilogInterfaceEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String resource = "config/config.json";

        Module module = ConfigParser.parse(resource);

        VerilogInterfaceEmitter emitter = new VerilogInterfaceEmitter(module);

        System.out.println(emitter.emitToString());
    }
}
