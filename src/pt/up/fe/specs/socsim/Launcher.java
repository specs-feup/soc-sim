package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.config.VltFileEmitter;
import pt.up.fe.specs.socsim.emitter.dpi.DpiHeaderEmitter;
import pt.up.fe.specs.socsim.emitter.dpi.DpiSourceEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.parser.ModuleParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {

        String resource = "/config.json";

        Module module = ModuleParser.parse(resource);

        DpiSourceEmitter emitter = new DpiSourceEmitter(module);

        System.out.println(emitter.emit());
    }
}
