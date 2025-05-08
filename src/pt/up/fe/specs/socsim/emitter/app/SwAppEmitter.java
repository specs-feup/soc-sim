package pt.up.fe.specs.socsim.emitter.app;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupString;
import pt.up.fe.specs.socsim.emitter.Emitter;
import pt.up.fe.specs.socsim.model.Module;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SwAppEmitter implements Emitter {
    private final String TEMPLATE_FILE = "/sw_app.stg";
    private final String TEMPLATE_NAME = "app";
    private final Module module;
    private final STGroup templates;

    public SwAppEmitter(Module module) {
        this.module = module;
        this.templates = this.load();
    }

    private STGroup load() {
        try {
            InputStream in = getClass().getResourceAsStream(TEMPLATE_FILE);
            if (in == null) {
                throw new IllegalStateException("Template file not found: " + TEMPLATE_FILE);
            }

            STGroup group = new STGroupString(new String(in.readAllBytes(), StandardCharsets.UTF_8));
            group.load();

            return group;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load templates from " + TEMPLATE_FILE, e);
        }
    }

    @Override
    public String emit() {
        ST template = this.templates.getInstanceOf(TEMPLATE_NAME);
        if (template == null)
            throw new IllegalStateException("Template '" + TEMPLATE_NAME + "' not found in " + TEMPLATE_FILE);

        template.add("moduleLower", this.module.name().toLowerCase())
                .add("moduleUpper", this.module.name().toUpperCase())
                .add("moduleOffset", this.module.offset().toString())
                .add("moduleSize", this.module.size().toString());

        return template.render();
    }
}
