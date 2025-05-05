package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.dpi.DPI;
import pt.up.fe.specs.socsim.model.interfaces.Interfaces;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.model.register.RegisterType;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleParser {

    private static String parseName(JsonReader reader) {
        return reader.getStringOrDefault("name", "unknown");
    }

    private static Interfaces parseInterfaces(JsonReader reader) {
        JsonReader itfsReader = reader.getObject("interfaces").orElseThrow();

        return new Interfaces(
            itfsReader.getBooleanOrDefault("reg", true),
            itfsReader.getBooleanOrDefault("obi_master", true),
            itfsReader.getBooleanOrDefault("obi_slave", true)
        );
    }

    private static Register parseRegister(JsonReader reader) {
        String name = reader.getStringOrDefault("name", "unknown");
        RegisterType type = RegisterType.fromString(reader.getStringOrDefault("type", "unknown"));
        Integer width = reader.getIntOrDefault("width", -1);
        Integer initial = reader.getIntOrDefault("initial", 0);

        return new Register(name, type, width, initial);
    }

    private static List<Register> parseRegisters(JsonReader reader) {
        List<JsonReader> regReader = reader.getArray("registers");
        if (regReader.isEmpty())
            return new ArrayList<>();

        List<Register> registers = new ArrayList<>();

        regReader.forEach(reg -> registers.add(parseRegister(reg)));

        return registers;
    }

    private static DPI parseDPI(JsonReader reader) {
        JsonReader dpiReader = reader.getObject("dpi").orElseThrow();

        List<String> send = dpiReader.getStringList("send").orElseGet(ArrayList::new);
        List<String> recv = dpiReader.getStringList("recv").orElseGet(ArrayList::new);

        return new DPI(send, recv);
    }

    public static Module parse(String resource) throws IOException {
        JsonReader reader = new JsonReader(resource).getObject("module").orElseThrow();

        String name = parseName(reader);
        Interfaces interfaces = parseInterfaces(reader);
        List<Register> registers = parseRegisters(reader);
        DPI dpi = parseDPI(reader);

        return new Module(name, interfaces, registers, dpi);
    }
}
