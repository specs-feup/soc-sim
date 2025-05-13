package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String configPath = args[0];

        Config config = ConfigParser.parse(configPath);

        Module module = config.module();

        System.out.println("Module name: " + module.name());
    }
}
