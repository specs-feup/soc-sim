package pt.up.fe.specs.socsim.model;

import pt.up.fe.specs.socsim.model.signal.Signal;

import java.util.List;

public record Module(String name, String description, List<Signal> signals) { }
