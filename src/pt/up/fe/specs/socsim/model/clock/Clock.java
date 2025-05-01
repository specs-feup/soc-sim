package pt.up.fe.specs.socsim.model.clock;

import pt.up.fe.specs.socsim.model.Direction;

public record Clock(String name, Direction direction, Edge edge) { }
