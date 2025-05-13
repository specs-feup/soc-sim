package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.emitter.template.dpi.DpiSourceFileEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.model.config.SocketOptions;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        DpiSourceFileEmitter emitter = new DpiSourceFileEmitter(ConfigParser.parse(args[0]));

        System.out.println(emitter.emitToString());
    }
}
