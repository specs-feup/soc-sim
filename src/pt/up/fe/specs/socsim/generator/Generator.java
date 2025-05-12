package pt.up.fe.specs.socsim.generator;

import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.emitter.EmitterTask;
import pt.up.fe.specs.socsim.emitter.template.app.SwAppEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.config.ConfigFileType;
import pt.up.fe.specs.socsim.emitter.template.dpi.DpiHeaderFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.dpi.DpiSourceFileEmitter;
import pt.up.fe.specs.socsim.emitter.template.verilog.VerilogInterfaceEmitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Generator {
    private static final String ROOT_DIR = "/home/pedro-ramalho/x-heep";

    private final Module module;
    private final List<EmitterTask> emitterTasks;

    public Generator(String configPath) throws IOException {
        this.module = ConfigParser.parse(configPath);
        this.emitterTasks = buildEmitterTasks();
    }

    public void run() {
        emitterTasks.forEach(this::emitToFile);
    }

    public void undo() {
        emitterTasks.forEach(this::deleteFile);
    }

    private void emitToFile(EmitterTask task) {
        try {
            Path filePath = Path.of(task.path());

            Files.createDirectories(filePath.getParent());

            task.emitter().emitToFile(task.path());

            System.out.println("Generated: " + task.path());
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to write file: " + task.path(), e);
        }
    }

    private void deleteFile(EmitterTask task) {
        Path path = Path.of(task.path());
        try {
            Files.deleteIfExists(path);
            System.out.println("Deleted: " + path);
        } catch (IOException e) {
            System.err.println("Failed to delete: " + path + " (" + e.getMessage() + ")");
        }
    }

    private List<EmitterTask> buildEmitterTasks() {
        return List.of(
            task(new VerilogInterfaceEmitter(module), "/hw/ip_examples/%s/%s.sv"),
            task(new ConfigFileEmitter(module, ConfigFileType.VLT), "/hw/ip_examples/%s/%s.vlt"),
            task(new ConfigFileEmitter(module, ConfigFileType.VERILOG), "/hw/ip_examples/%s/%s.core"),
            task(new DpiHeaderFileEmitter(module), "/hw/ip_examples/%s/dpi/%sdpi.h"),
            task(new DpiSourceFileEmitter(module), "/hw/ip_examples/%s/dpi/%sdpi.c"),
            task(new ConfigFileEmitter(module, ConfigFileType.DPI), "/hw/ip_examples/%s/dpi/%s.core"),
            task(new SwAppEmitter(module), "/sw/applications/%s/main.c")
        );
    }

    private EmitterTask task(Emitter emitter, String relativeTemplate) {
        String formattedPath = String.format(
            relativeTemplate.replaceAll("%s", "%1\\$s"),
            module.name()
        );

        return new EmitterTask(emitter, ROOT_DIR + formattedPath);
    }
}
