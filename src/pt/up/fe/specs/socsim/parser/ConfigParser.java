package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.model.config.Paths;
import pt.up.fe.specs.socsim.model.config.SocketOptions;
import pt.up.fe.specs.socsim.model.config.communication.Communication;
import pt.up.fe.specs.socsim.model.config.communication.CommunicationProtocol;
import pt.up.fe.specs.socsim.model.config.endpoint.Endpoint;
import pt.up.fe.specs.socsim.model.config.endpoint.EndpointMode;
import pt.up.fe.specs.socsim.model.interfaces.Interfaces;
import pt.up.fe.specs.socsim.model.register.Register;
import pt.up.fe.specs.socsim.model.register.RegisterDpiType;
import pt.up.fe.specs.socsim.model.register.RegisterVerilogType;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.*;

public class ConfigParser {
    public static Config parse(String path) throws IOException {
        JsonReader root = new JsonReader(path);

        Module module = parseModule(root.getObject("module").orElseThrow());
        Paths paths = parsePaths(root.getObject("paths").orElseThrow());
        Communication communication = parseCommunication(root.getObject("communication").orElseThrow());

        return new Config(paths, communication, module);
    }

    private static Module parseModule(JsonReader reader) {
        String name = reader.getStringOrDefault("name", "unknown");
        Integer offset = reader.getIntOrDefault("offset", 0);
        Integer size = reader.getIntOrDefault("size", 0);

        Interfaces interfaces = parseInterfaces(reader);

        List<Register> registers = parseRegisters(reader);

        return new Module(name, interfaces, registers, offset, size);
    }

    private static Interfaces parseInterfaces(JsonReader reader) {
        JsonReader itfsReader = reader.getObject("interfaces").orElseThrow();

        return new Interfaces(
            itfsReader.getBooleanOrDefault("reg", true),
            itfsReader.getBooleanOrDefault("obi_master", true),
            itfsReader.getBooleanOrDefault("obi_slave", true)
        );
    }

    private static List<Register> parseRegisters(JsonReader reader) {
        List<JsonReader> regReader = reader.getArray("registers");
        if (regReader.isEmpty())
            return new ArrayList<>();

        List<Register> registers = new ArrayList<>();

        regReader.forEach(reg -> registers.add(parseRegister(reg)));

        return registers;
    }

    private static Register parseRegister(JsonReader reader) {
        String name = reader.getStringOrDefault("name", "unknown");
        RegisterVerilogType verilogType = RegisterVerilogType.fromString(reader.getStringOrDefault("verilog_type", "unknown"));
        RegisterDpiType dpiType = RegisterDpiType.fromString(reader.getStringOrDefault("dpi_type", "unknown"));
        Integer width = reader.getIntOrDefault("width", 0);
        Integer initial = reader.getIntOrDefault("initial", 0);

        return new Register(name, verilogType, dpiType, width, initial);
    }

    private static Paths parsePaths(JsonReader reader) {
        String soc = reader.getStringOrDefault("soc", "unknown");
        String sim = reader.getStringOrDefault("sim", "unknown");

        return new Paths(soc, sim);
    }

    private static Communication parseCommunication(JsonReader reader) {
        CommunicationProtocol protocol = CommunicationProtocol.fromString(
                reader.getStringOrDefault("protocol", "unknown")
        );
        JsonReader settings = reader.getObject("settings").orElseThrow();

        Endpoint e1 = parseEndpoint(settings.getObject("client").orElseThrow());
        Endpoint e2 = parseEndpoint(settings.getObject("server").orElseThrow());

        return new Communication(protocol, e1, e2);
    }

    private static Endpoint parseEndpoint(JsonReader reader) {
        String address = reader.getStringOrDefault("address", "unknown");

        SocketOptions options = new SocketOptions(
            reader.getIntOrDefault("send_timeout", 0),
            reader.getIntOrDefault("recv_timeout", 0),
            reader.getIntOrDefault("retries", 0)
        );

        return new Endpoint(address, options);
    }
}
