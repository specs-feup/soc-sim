package pt.up.fe.specs.socsim.model.reset;

import pt.up.fe.specs.socsim.model.signal.IOType;
import pt.up.fe.specs.socsim.model.signal.Signal;

public class Reset extends Signal {
    private final Active active;

    public Reset(String name, IOType io, Active active) {
        super(name, io);

        this.active = active;
    }

    public Active getActive() {
        return this.active;
    }
}
