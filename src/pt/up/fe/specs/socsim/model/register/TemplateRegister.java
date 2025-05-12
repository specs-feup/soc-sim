package pt.up.fe.specs.socsim.model.register;

public class TemplateRegister {
    public final String name;
    public final String verilogType;
    public final int width;
    public final int widthMinusOne;
    public final int initial;

    public TemplateRegister(Register reg) {
        this.name = reg.name();
        this.verilogType = reg.verilogType().getType();
        this.width = reg.width();
        this.widthMinusOne = reg.width() - 1;
        this.initial = reg.initial();
    }
}
