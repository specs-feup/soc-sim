package pt.up.fe.specs.socsim.emitter.template.config;

public enum ConfigFileType {
    DPI("config_dpi"),
    VERILOG("config_verilog"),
    VLT("config_vlt");

    private final String templateName;

    ConfigFileType(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateName() {
        return templateName;
    }
}
