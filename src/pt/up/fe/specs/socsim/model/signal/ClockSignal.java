package pt.up.fe.specs.socsim.model.signal;

import pt.up.fe.specs.socsim.model.signal.enums.Edge;
import pt.up.fe.specs.socsim.model.signal.enums.IO;

public record ClockSignal(String name, IO io, Edge edge) implements Signal { }
