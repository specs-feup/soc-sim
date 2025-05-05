package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.sv.InterfaceEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.dpi.DPI;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;
import java.util.List;

public class Launcher {
    public static void main(String[] args) throws IOException {

        String resource = "/config.json";

        Module module = ModuleParser.parse(resource);

        InterfaceEmitter emitter = new InterfaceEmitter(module);

        System.out.println(emitter.emit());
    }
}
