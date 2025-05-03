package pt.up.fe.specs.socsim.model.signal;

import pt.up.fe.specs.socsim.model.signal.enums.Active;
import pt.up.fe.specs.socsim.model.signal.enums.IO;

public record ResetSignal(String name, IO io, Active active) implements Signal { }
