package pt.up.fe.specs.socsim.emitter;

import pt.up.fe.specs.socsim.model.Module;

import java.util.stream.Collectors;

public class ModuleEmitter {
    private StringBuilder sb;
    private final Module module;

    public ModuleEmitter(Module module) {
        this.module = module;
        this.sb = new StringBuilder();
    }

    private void emitHeader() {
        this.sb.append("module ").append(module.name()).append(" #(\n");
    }

    private void emitParameters() {
        // TODO
    }

    private void emitSignals() {
        this.sb.append(this.module.signals().stream()
                .map(sig -> String.format("    %s %s %s",
                        sig.io().getIO(), sig.type().getType(), sig.name()))
                .collect(Collectors.joining(",\n"))).append("\n");
    }

    private void emitFooter() {
        this.sb.append("\nendmodule\n");
    }

    public String emitToString() {
        this.emitHeader();
        this.emitParameters();

        this.sb.append(") (\n");

        this.emitSignals();

        this.sb.append(");\n\n");

        this.emitFooter();

        return this.sb.toString();
    }

    public static void emitToFile(String filepath) {

    }
}
