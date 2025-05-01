package pt.up.fe.specs.socsim.model.interfaces;

import pt.up.fe.specs.socsim.model.signal.Signal;

import java.util.List;

public record Interface(InterfaceType type, List<Signal> signals) { }
