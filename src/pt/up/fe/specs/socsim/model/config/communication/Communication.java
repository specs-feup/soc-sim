package pt.up.fe.specs.socsim.model.config.communication;

import pt.up.fe.specs.socsim.model.config.endpoint.Endpoint;

public record Communication(CommunicationProtocol protocol, Endpoint e1, Endpoint e2) { }
