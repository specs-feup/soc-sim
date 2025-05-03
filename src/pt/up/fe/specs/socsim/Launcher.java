package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.ModuleEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.signal.Signal;
import pt.up.fe.specs.socsim.parser.ModuleParser;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String resource = "/config.json";
        ModuleParser parser = new ModuleParser(resource);

        Module module = parser.parse();

        ModuleEmitter emitter = new ModuleEmitter(module);

        String sv = emitter.emitToString();

        System.out.println(sv);
    }
}
