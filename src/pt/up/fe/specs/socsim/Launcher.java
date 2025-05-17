package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.app.SwAppEmitter;
import pt.up.fe.specs.socsim.generator.Generator;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        String path = args[0];

        Config config = ConfigParser.parse(path);

        SwAppEmitter emitter = new SwAppEmitter(config);

        System.out.println(emitter.emitToString());
    }
}
