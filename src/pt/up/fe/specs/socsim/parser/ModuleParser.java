package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.interfaces.Interface;
import pt.up.fe.specs.socsim.model.interfaces.InterfaceType;
import pt.up.fe.specs.socsim.model.signal.*;
import pt.up.fe.specs.socsim.model.signal.enums.Active;
import pt.up.fe.specs.socsim.model.signal.enums.Edge;
import pt.up.fe.specs.socsim.model.signal.enums.IO;
import pt.up.fe.specs.socsim.model.signal.enums.Role;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.util.ArrayList;
import java.util.List;

public class ModuleParser {
    public static Module parse(JsonReader reader) {
        JsonReader node = reader.getObject("module").orElseThrow(() -> new IllegalArgumentException("Missing 'module' object!"));

        String name = node.getStringOrDefault("name", "unknown");
        String description = node.getStringOrDefault("description", "unknown");

        List<Interface> interfaces = parseInterfaces(node.getArray("interfaces"));

        return new Module(name, description, interfaces);
    }

    private static List<Interface> parseInterfaces(List<JsonReader> readers) {
        List<Interface> interfaces = new ArrayList<>();

        for (JsonReader rd : readers) {
            InterfaceType type = InterfaceType.fromString(rd.getStringOrDefault("type", "unknown"));

            List<Signal> signals = parseSignals(type, rd.getArray("signals"));

            interfaces.add(new Interface(type, signals));
        }

        return interfaces;
    }

    private static List<Signal> parseSignals(InterfaceType type, List<JsonReader> readers) {
        List<Signal> signals = new ArrayList<>();

        for (JsonReader rd : readers) {
            String name = rd.getStringOrDefault("name", "unknown");
            IO io = IO.fromString(rd.getStringOrDefault("io", "unknown"));

            switch (type) {
                case CLK -> signals.add(new ClockSignal(name, io, Edge.fromString(rd.getStringOrDefault("edge", "unknown"))));
                case RST -> signals.add(new ResetSignal(name, io, Active.fromString(rd.getStringOrDefault("active", "unknown"))));
                case REG -> signals.add(new RegSignal(name, io, Role.fromString(rd.getStringOrDefault("role", "unknown"))));
                case OBI -> signals.add(new ObiSignal(name, io, Role.fromString(rd.getStringOrDefault("role", "unknown"))));
                default -> throw new IllegalArgumentException("Found an interface of unknown type: " + type.getType());
            }
        }

        return signals;
    }
}
