package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.interfaces.Interface;
import pt.up.fe.specs.socsim.model.signal.Signal;
import pt.up.fe.specs.socsim.parser.ModuleParser;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String filepath = "/config.json";

        JsonReader reader = new JsonReader(filepath);

        Module module = ModuleParser.parse(reader);

        System.out.println("Module name: " + module.name());
        System.out.println("Module description: " + module.description());
        for (Interface iface : module.interfaces()) {
            System.out.println("- Interface type: " + iface.type());

            for (Signal sig : iface.signals()) {
                System.out.println("    * Signal: " + sig);
            }
        }
    }
}
