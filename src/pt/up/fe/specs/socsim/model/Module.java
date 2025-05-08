package pt.up.fe.specs.socsim.model;


import pt.up.fe.specs.socsim.model.dpi.DPI;
import pt.up.fe.specs.socsim.model.interfaces.Interfaces;
import pt.up.fe.specs.socsim.model.register.Register;

import java.util.List;

public record Module(String name, Interfaces interfaces, List<Register> registers, Integer offset, Integer size) { }
