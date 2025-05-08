package pt.up.fe.specs.socsim.emitter.config;

import pt.up.fe.specs.socsim.emitter.Emitter;

import java.util.ArrayList;
import java.util.List;

public class CoreFileEmitter implements Emitter {
    private final String moduleName;
    private final String description;
    private final List<FileEntry> files = new ArrayList<>();
    private String version = "";

    private record FileEntry(String name, String type, Boolean isInclude) { }

    public CoreFileEmitter(String moduleName, String description) {
        this.moduleName = moduleName;
        this.description = description;
    }

    public CoreFileEmitter withVersion(String version) {
        this.version = version;

        return this;
    }

    public CoreFileEmitter withFile(String fileName, String type, Boolean isInclude) {
        this.files.add(new FileEntry(fileName, type, isInclude));

        return this;
    }

    public String emit() {
        StringBuilder sb = new StringBuilder();

        sb.append("CAPI=2:\n\n");

        sb.append("name: \"").append("example:ip:").append(this.moduleName);
        if (!version.isEmpty()) {
            sb.append(":").append(this.version);
        }
        sb.append("\"\n");

        sb.append("description: \"").append(description).append("\"\n\n");

        sb.append("filesets:\n  files_rtl:\n     files:\n");
        for (FileEntry file : this.files) {
            sb.append("      - ");
            if (file.isInclude != null || file.type.contains("Source")) {
                sb.append(file.name).append(": { file_type: ").append(file.type);
                if (file.isInclude != null) {
                    sb.append(", is_include_file: ").append(file.isInclude);
                }
                sb.append(" }\n");
            } else {
                sb.append(file.name).append("\n");
            }
        }

        sb.append("\ntargets:\n  default:\n    filesets:\n      - files_rtl");

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
