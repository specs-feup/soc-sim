package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.generator.Generator;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Config config = ConfigParser.parse("config/config.json");

        Module module = config.module();

        System.out.println("Module name: " + module.name());
    }
}
