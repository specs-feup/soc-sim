package pt.up.fe.specs.socsim.model.config.endpoint;

import pt.up.fe.specs.socsim.model.config.SocketOptions;

public record Endpoint(EndpointMode mode, String address, SocketOptions sockopts) { }
