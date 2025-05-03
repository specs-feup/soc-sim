package pt.up.fe.specs.socsim.parser;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.signal.Signal;
import pt.up.fe.specs.socsim.model.signal.SignalIO;
import pt.up.fe.specs.socsim.model.signal.SignalType;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleParser {
    private Module module;

    private final JsonReader reader;

    public ModuleParser(String resource) throws IOException {
        this.reader = new JsonReader(resource).getObject("module").orElseThrow();
    }

    private List<Signal> parseSignals() {
        List<Signal> signals = new ArrayList<>();

        List<JsonReader> signalsReader = this.reader.getArray("signals");

        signalsReader.forEach(
            signal -> signals.add( new Signal(
                signal.getStringOrDefault("name", "unknown"),
                SignalIO.fromString(signal.getStringOrDefault("io", "unknown")),
                SignalType.fromString(signal.getStringOrDefault("type", "unknown"))
            ))
        );

        return signals;
    }

    public Module parse() {
        String name = this.reader.getStringOrDefault("name", "unknown");
        String description = this.reader.getStringOrDefault("description", "unknown");
        List<Signal> signals = this.parseSignals();

        return new Module(name, description, signals);
    }
}
