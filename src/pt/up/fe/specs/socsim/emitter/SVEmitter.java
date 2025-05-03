package pt.up.fe.specs.socsim.emitter;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.signal.Signal;

import java.time.Clock;

public class SVEmitter {
    private final Module module;
    private final StringBuilder sb;
    private final String indent = "    ";

    public SVEmitter(Module module) {
        this.module = module;
        this.sb = new StringBuilder();
    }

    public String emitToString() {
        return "";
    }

    public void emitToFile(String filepath) {

    }

    private String emitHeader() {
        return String.format("module %s #(", module.name());
    }

    private void emitParameters() {

    }

    private void emitSignals() {

    }

    private void emitFooter() {

    }
}
