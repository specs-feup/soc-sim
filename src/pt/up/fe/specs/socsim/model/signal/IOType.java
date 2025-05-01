package pt.up.fe.specs.socsim.model.signal;

public enum IOType {
    INPUT("input"),
    OUTPUT("output");

    private final String io;

    IOType(String io) {
        this.io = io;
    }

    public String getIO() {
        return this.io;
    }

    public static IOType fromString(String ioString) {
        for (IOType type : IOType.values()) {
            if (type.io.equals(ioString)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Unknown IO type: " + ioString);
    }
}
