package pt.up.fe.specs.socsim.model.config.communication;

import pt.up.fe.specs.socsim.model.register.RegisterDpiType;

public enum CommunicationProtocol {
    ZMQ("zmq"),
    TCP("tcp");

    private final String protocol;

    CommunicationProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public static CommunicationProtocol fromString(String protocolString) {
        for (CommunicationProtocol v : CommunicationProtocol.values())
            if (v.protocol.equals(protocolString))
                return v;

        throw new IllegalArgumentException("Unknown register type: " + protocolString);
    }
}
