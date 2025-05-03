package pt.up.fe.specs.socsim.model.signal.enums;

public enum IO {
    INPUT("input"),
    OUTPUT("output");

    private final String io;

    IO(String io) {
        this.io = io;
    }

    public String getIO() {
        return this.io;
    }

    public static IO fromString(String ioString) {
        for (IO type : IO.values()) {
            if (type.io.equals(ioString)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown IO type: " + ioString);
    }
}
