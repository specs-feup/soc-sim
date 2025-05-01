package pt.up.fe.specs.socsim.model.signal;

public abstract class Signal {
    private final String name;
    private final IOType io;

    public Signal(String name, IOType io) {
        this.name = name;
        this.io = io;
    }

    public String getName() { return this.name; }

    public IOType getIOType() { return this.io; }
}
