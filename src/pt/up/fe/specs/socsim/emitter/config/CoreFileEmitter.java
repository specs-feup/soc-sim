package pt.up.fe.specs.socsim.emitter.config;

import pt.up.fe.specs.socsim.emitter.Emitter;

public class CoreFileEmitter {
    private final String moduleName;
    private final StringBuilder sb;

    public CoreFileEmitter(String moduleName) {
        this.moduleName = moduleName;
        this.sb = new StringBuilder();
    }

    public String emit(String description, String files, String filetype) {
        this.sb.append("CAPI=2:\n\n")
                .append("name: ").append(String.format("example:ip:%s\n", this.moduleName))
                .append("description: ").append(description).append("\n\n")
                .append("filesets:\n  files_rtl:\n    files:\n").append(files);

        this.sb.append("\n\n");

        if (filetype != null) {
            this.sb.append("      ").append(filetype);
        }

        this.sb.append("targets:\n  default:  \n    filesets:  \n      - files_rtl");

        return this.sb.toString();
    }
}
