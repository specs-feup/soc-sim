package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.interfaces.Interface;
import pt.up.fe.specs.socsim.model.interfaces.InterfaceType;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigParser {
    private Module module;
    private String name;
    private String description;
    private List<Interface> interfaces;
    private JsonReader reader;

    public ConfigParser(String filepath) throws IOException {
        this.reader = new JsonReader(filepath);

        this.interfaces = new ArrayList<>();
    }

    private void dealWithModuleName() {
        this.name = this.reader.getStringOrDefault("module.name", "");
    }

    private void dealWithModuleDescription() {
        this.description = this.reader.getStringOrDefault("module.description", "");
    }

    private List<GenericSignal> dealWithInterfaceSignals(InterfaceType type, JsonReader ireader) {

        return null;
    }

    private void dealWithInterface() {

    }
}
