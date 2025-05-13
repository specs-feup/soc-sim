package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.generator.Generator;

import java.io.IOException;

public class Launcher {
    public static void main(String[] args) throws IOException {
        Generator generator = new Generator("config/config.json");
        generator.run();
    }
}
