package pt.up.fe.specs.socsim.model.config.endpoint;

import pt.up.fe.specs.socsim.model.config.communication.CommunicationProtocol;

public enum EndpointMode {
    BIND("bind"),
    CONNECT("tcp");

    private final String mode;

    EndpointMode(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return this.mode;
    }

    public static EndpointMode fromString(String modeString) {
        for (EndpointMode v : EndpointMode.values())
            if (v.mode.equals(modeString))
                return v;

        throw new IllegalArgumentException("Unknown register type: " + modeString);
    }
}
