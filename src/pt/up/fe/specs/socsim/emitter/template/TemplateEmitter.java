package pt.up.fe.specs.socsim.emitter.template;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.model.ModuleTemplateData;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class TemplateEmitter implements Emitter {
    private final Module module;
    private final STGroup templates;
    private final String templateFile;

    protected TemplateEmitter(Module module, String templateFile) {
        this.module = module;
        this.templateFile = templateFile;
        this.templates = load();
    }

    protected abstract String getTemplateName();

    protected STGroup load() {
        try {
            InputStream in = getClass().getResourceAsStream(this.templateFile);
            if (in == null) {
                throw new IllegalStateException("Template file not found: " + this.templateFile);
            }

            STGroup group = new STGroupString(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            group.load();

            return group;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load templates from " + this.templateFile, e);
        }
    }

    @Override
    public String emitToString() {
        ST template = templates.getInstanceOf(getTemplateName());
        if (template == null)
            throw new IllegalStateException("Template '" + getTemplateName() + "' not found in " + templateFile);

        template.add("module", new ModuleTemplateData(this.module));

        return template.render();
    }

    @Override
    public void emitToFile(String filepath) {
        try {
            Path file = Path.of(filepath);

            Files.createDirectories(file.getParent());
            Files.writeString(file, this.emitToString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write to file: " + filepath, e);
        }
    }
}
