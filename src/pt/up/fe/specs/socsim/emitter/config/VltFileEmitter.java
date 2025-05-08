package pt.up.fe.specs.socsim.emitter.config;

import pt.up.fe.specs.socsim.emitter.Emitter;

public class VltFileEmitter implements Emitter {
    private final String moduleName;

    public VltFileEmitter(String moduleName) {
        this.moduleName = moduleName;
    }

    public String emit() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n");
        sb.append("`verilator_config\n\n");

        sb.append("lint_off -rule UNUSED file ")
                .append(String.format("\"*/%s/%s.sv\"", this.moduleName, this.moduleName))
                .append(" -match \"*\"");

        return sb.toString();
    }

    @Override
    public String emitToString() {
        return null;
    }

    @Override
    public void emitToFile(String filepath) {

    }
}
