package pt.up.fe.specs.socsim.generator;

import pt.up.fe.specs.socsim.emitter.EmitterTask;

import java.util.List;

public class Generator {
    private final Module module;

    public Generator(Module module) { this.module = module; }

    private void emitToFile(EmitterTask task) {
        
    }

    public void run(String dir) {
        List<EmitterTask> tasks = List.of();
    }
}
