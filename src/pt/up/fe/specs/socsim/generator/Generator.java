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
import pt.up.fe.specs.socsim.model.config.Config;
import pt.up.fe.specs.socsim.modifier.ModifierTask;
import pt.up.fe.specs.socsim.modifier.core.CoreFileModifier;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessModifier;
import pt.up.fe.specs.socsim.modifier.verilog.TestHarnessPkgModifier;
import pt.up.fe.specs.socsim.parser.ConfigParser;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Generator {
    private static final String ROOT_DIR = "/home/pedro-ramalho/x-heep";
    private final Config config;
    private final Module module;
    private final List<EmitterTask> emitterTasks;
    private final List<ModifierTask> modifierTasks;

    public Generator(String configPath) throws IOException {
        this.config = ConfigParser.parse(configPath);

        this.module = this.config.module();

        this.emitterTasks = buildEmitterTasks();
        this.modifierTasks = buildModifierTasks();
    }

    public void run() {
        emitterTasks.forEach(this::emitToFile);
        modifierTasks.forEach(this::applyModifier);
    }

    public void undo() {
        emitterTasks.forEach(task -> deleteFile(task.path()));
        modifierTasks.forEach(task -> restoreOriginalFile(task.path()));
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

    private List<ModifierTask> buildModifierTasks() throws IOException {
        return List.of(
            new ModifierTask(new TestHarnessPkgModifier(module, ROOT_DIR + "/tb/testharness_pkg.sv"), ROOT_DIR + "/tb/testharness_pkg.sv"),
            new ModifierTask(new TestHarnessModifier(module, ROOT_DIR + "/tb/testharness.sv"), ROOT_DIR + "/tb/testharness.sv"),
            new ModifierTask(new CoreFileModifier(module, ROOT_DIR + "/x-heep-tb-utils.core"), ROOT_DIR + "/x-heep-tb-utils.core")
        );
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

    private void applyModifier(ModifierTask task) {
        try {
            backupOriginal(task.path());

            task.modifier().modifyToFile(task.path());

            System.out.println("Modified file: " + task.path());
        } catch (Exception e) {
            System.err.println("Failed to modify file: " + task.path() + " (" + e.getMessage() + ")");
        }
    }

    private void deleteFile(String path) {
        try {
            Files.deleteIfExists(Paths.get(path));

            System.out.println("Deleted: " + path);
        } catch (IOException e) {
            System.err.println("Failed to delete: " + path);
        }
    }

    private void backupOriginal(String path) throws IOException {
        Path filePath = Paths.get(path);
        Path backup = filePath.resolveSibling(filePath.getFileName() + ".bak");

        Files.copy(filePath, backup, StandardCopyOption.REPLACE_EXISTING);
    }

    private void restoreOriginalFile(String path) {
        Path filePath = Paths.get(path);
        Path backup = filePath.resolveSibling(filePath.getFileName() + ".bak");

        try {
            if (Files.exists(backup)) {
                Files.copy(backup, filePath, StandardCopyOption.REPLACE_EXISTING);
                Files.delete(backup);

                System.out.println("Restored: " + path);
            }
        } catch (IOException e) {
            System.err.println("Failed to restore: " + path);
        }
    }

    private EmitterTask task(Emitter emitter, String relativeTemplate) {
        String formattedPath = String.format(
            relativeTemplate.replaceAll("%s", "%1\\$s"),

            module.name()
        );
        return new EmitterTask(emitter, ROOT_DIR + formattedPath);
    }

    private String path(String relativePath) {
        return ROOT_DIR + relativePath;
    }
}
