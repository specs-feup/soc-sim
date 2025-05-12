package pt.up.fe.specs.socsim.model.register;

public enum RegisterVerilogType {
    LOGIC("logic"),
    BIT("bit");

    private String type;

    RegisterVerilogType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static RegisterVerilogType fromString(String typeString) {
        for (RegisterVerilogType v : RegisterVerilogType.values())
            if (v.type.equals(typeString))
                return v;

        throw new IllegalArgumentException("Unknown register type: " + typeString);
    }
}
