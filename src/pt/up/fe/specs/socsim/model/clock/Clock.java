package pt.up.fe.specs.socsim.model.clock;

import pt.up.fe.specs.socsim.model.signal.IOType;
import pt.up.fe.specs.socsim.model.signal.Signal;

public class Clock extends Signal {
    private final Edge edge;

    public Clock(String name, IOType io, Edge edge) {
        super(name, io);

        this.edge = edge;
    }

    public Edge getEdge() {
        return this.edge;
    }
}
