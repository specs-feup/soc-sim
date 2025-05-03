package pt.up.fe.specs.socsim.model.signal;

public enum SignalIO {
    INPUT("input"),
    OUTPUT("output");

    private final String io;

    SignalIO(String io) {
        this.io = io;
    }

    public String getIO() {
        return this.io;
    }

    public static SignalIO fromString(String ioString) {
        for (SignalIO value : SignalIO.values()) {
            if (value.io.equals(ioString)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Unknown signal IO: " + ioString);
    }
}
