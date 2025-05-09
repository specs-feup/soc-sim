package pt.up.fe.specs.socsim.modifier;

import pt.up.fe.specs.socsim.model.Module;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class BaseModifier implements Modifier {
    private final Module module;
    private final String code;

    protected BaseModifier(Module module, String filepath) throws IOException {
        this.module = module;
        this.code = this.read(filepath);
    }

    protected String read(String filepath) throws IOException {
        return Files.readString(Path.of(filepath));
    }

    protected Module getModule() { return this.module; }

    protected String getCode() { return this.code; }
}
