package pt.up.fe.specs.socsim.modifier.core;

import pt.up.fe.specs.socsim.model.Module;
import pt.up.fe.specs.socsim.modifier.BaseModifier;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreFileModifier extends BaseModifier {
    private static final String FILESET_DEPEND_PATTERN = "files_examples:\\s*\\n\\s*depend:\\s*\\n(.*?)(?=\\s*files:)";
    private static final String VERILATOR_WAIVER_PATTERN = "files_verilator_waiver:\\s*\\n\\s*files:\\s*\\n(.*?)(?=\\s*file_type:)";
    private static final String TARGET_FILESETS_PATTERN = "default:.*?filesets:\\s*\\n(.*?)(?=\\s*toplevel:)";
    private static final String UARTDPI_ENTRY_PATTERN = "(uartdpi:\\s*\\n\\s*depend:\\s*\\n\\s*- lowrisc:dv_dpi:uartdpi\\s*\\n)";
    public CoreFileModifier(Module module, String filepath) throws IOException {
        super(module, filepath);
    }

    @Override
    public String modifyToString() {
        String content = getCode();

        content = insertDependency(content, FILESET_DEPEND_PATTERN,
                "    - example:ip:" + getModule().name().toLowerCase());
        content = insertVerilatorWaiver(content);
        content = insertDpiDependencySection(content);
        content = insertDpiDependency(content);

        return content;
    }

    private String insertDependency(String content, String pattern, String newEntry) {
        Pattern p = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = p.matcher(content);

        if (m.find()) {
            String existingDeps = m.group(1).trim();

            String updatedDeps = "    " + existingDeps + "\n" + newEntry;

            return content.replaceFirst(Pattern.quote(m.group(1)), updatedDeps);
        }
        throw new IllegalStateException("Could not find dependency section in core file");
    }

    private String insertVerilatorWaiver(String content) {
        Pattern p = Pattern.compile(VERILATOR_WAIVER_PATTERN, Pattern.DOTALL);
        Matcher m = p.matcher(content);

        if (m.find()) {
            String existingFiles = m.group(1).trim();
            String[] files = existingFiles.split("\n");
            StringBuilder updatedFiles = new StringBuilder();

            for (String file : files) {
                if (!file.trim().equals("- tb/tb.vlt")) {
                    updatedFiles.append(file).append("\n");
                }
            }

            String newFile = String.format("    - hw/ip_examples/%s/%s.vlt",
                    getModule().name().toLowerCase(),
                    getModule().name().toLowerCase());
            updatedFiles.append(newFile).append("\n");

            updatedFiles.append("    - tb/tb.vlt");

            return content.replaceFirst(Pattern.quote(existingFiles), updatedFiles.toString());
        }

        throw new IllegalStateException("Could not find verilator waiver section in core file");
    }


    private String insertDpiDependencySection(String content) {
        Pattern p = Pattern.compile(UARTDPI_ENTRY_PATTERN, Pattern.DOTALL);
        Matcher m = p.matcher(content);

        if (m.find()) {
            String uartDpiEntry = m.group(1).trim();

            String moduleName = getModule().name().toLowerCase();
            String newDpiEntry = String.format(
                    "\n\n  %sdpi:\n    depend:\n      - example:ip:%sdpi:0.1",
                    moduleName,
                    moduleName
            );

            return content.replaceFirst(Pattern.quote(uartDpiEntry), uartDpiEntry + newDpiEntry);
        }

        throw new IllegalStateException("Could not find uartdpi section in core file");
    }


    private String insertDpiDependency(String content) {
        Pattern p = Pattern.compile(TARGET_FILESETS_PATTERN, Pattern.DOTALL);
        Matcher m = p.matcher(content);

        if (m.find()) {
            String existingFilesets = m.group(1).trim();

            String newDpi = String.format("    - tool_verilator? (%sdpi)",
                    getModule().name().toLowerCase());

            String updatedFilesets = "    " + existingFilesets + "\n" + newDpi;

            return content.replaceFirst(Pattern.quote(m.group(1)), updatedFilesets);
        }

        throw new IllegalStateException("Could not find target filesets section in core file");
    }
}
