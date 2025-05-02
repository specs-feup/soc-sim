package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Launcher {
    public static void main(String[] args) throws IOException {
        JsonReader reader = new JsonReader("config.json");

        String type = reader.getArrayElement("module.interfaces", 0).get().getString("type").get();

        System.out.println("Type: " + type);
    }
}
