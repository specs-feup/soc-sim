package pt.up.fe.specs.socsim.model.config.endpoint;

import pt.up.fe.specs.socsim.model.config.Sockopts;

public record Endpoint(String mode, String address, Sockopts sockopts) { }
