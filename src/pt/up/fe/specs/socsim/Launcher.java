package pt.up.fe.specs.socsim;

import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class Launcher {
    public static void main(String[] args) throws IOException {
        JsonReader reader = new JsonReader("config.json");

        Optional<String> module = reader.getString("module.name");

        reader.getString("module.name").ifPresent(s -> System.out.println("Module name: " + s));
        reader.getString("module.description").ifPresent(s -> System.out.println("Module description: " + s));

        List<JsonReader> interfaces = reader.getArray("module.interfaces");
        for (JsonReader itf : interfaces) {
            System.out.println("Interface type: " + itf.getString("type").get());

            List<JsonReader> signals = itf.getArray("signals");
            for (JsonReader signal : signals) {
                System.out.println("Signal name: " + signal.getString("name").get());
                System.out.println("Signal io: " + signal.getString("io").get());
            }
            System.out.println();
        }
    }
}
