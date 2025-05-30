package pt.up.fe.specs.socsim.model.register;

public record Register(String name, RegisterVerilogType verilogType, RegisterDpiType dpiType, Integer width, Integer initial) {
    public int widthMinusOne() { return this.width - 1; }
}
