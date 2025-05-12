package pt.up.fe.specs.socsim.model.register;

public enum RegisterDpiType {
    BYTE("byte"),
    SHORT("short"),
    INT("int"),
    LONG("long");

    private final String type;

    RegisterDpiType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static RegisterDpiType fromString(String typeString) {
        for (RegisterDpiType v : RegisterDpiType.values())
            if (v.type.equals(typeString))
                return v;


        throw new IllegalArgumentException("Unknown register type: " + typeString);
    }
}
