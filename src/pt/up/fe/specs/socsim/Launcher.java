package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.signal.Signal;
import pt.up.fe.specs.socsim.parser.ModuleParser;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String config = "/config.json";

        ModuleParser parser = new ModuleParser(config);

        Module module = parser.parse();

        System.out.println("Module name: " + module.name());
        System.out.println("Module description: " + module.description());
        for (Signal signal : module.signals()) {
            System.out.println("name: " + signal.name() + ", io: " + signal.io() + ", type: " + signal.type());
        }
    }
}
