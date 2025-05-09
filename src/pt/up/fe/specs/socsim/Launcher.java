package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileType;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.core.CoreFileModifier;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String resource = "/config/config.json";

        Module module = ModuleParser.parse(resource);

        ConfigFileEmitter emitter = new ConfigFileEmitter(module, ConfigFileType.VERILOG);

        System.out.println(emitter.emitToString());
    }
}
