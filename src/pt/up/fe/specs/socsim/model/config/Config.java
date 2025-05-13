package pt.up.fe.specs.socsim.model.config;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.config.communication.Communication;

public record Config(Paths paths, Communication communication, Module module) { }
