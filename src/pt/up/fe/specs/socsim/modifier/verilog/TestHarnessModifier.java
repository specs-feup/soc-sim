package pt.up.fe.specs.socsim.modifier.verilog;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.BaseModifier;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestHarnessModifier extends BaseModifier {

    protected TestHarnessModifier(Module module, String filepath) throws IOException {
        super(module, filepath);
    }

    @Override
    public String modifyToString() {
        return null;
    }

    @Override
    public void modifyToFile(String filepath) {

    }
}
