package pt.up.fe.specs.socsim.model.signal;

import pt.up.fe.specs.socsim.model.signal.enums.IO;
import pt.up.fe.specs.socsim.model.signal.enums.Role;

public record ObiSignal(String name, IO io, Role role) implements Signal { }
