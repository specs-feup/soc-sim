package pt.up.fe.specs.socsim.model.register;

public enum RegisterType {
    LOGIC("logic"),
    BIT("bit");

    private String type;

    RegisterType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static RegisterType fromString(String typeString) {
        for (RegisterType v : RegisterType.values())
            if (v.type.equals(typeString))
                return v;

        throw new IllegalArgumentException("Unknown register type: " + typeString);
    }
}
