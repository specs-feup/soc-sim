package pt.up.fe.specs.socsim.model;

public enum InterfaceType {
    REG("reg"),
    OBI("obi"),
    CLK("clock"),
    RST("reset");

    private String type;

    InterfaceType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static InterfaceType fromString(String type) {
        for (InterfaceType t : InterfaceType.values()) {
            if (t.type.equals(type)) {
                return t;
            }
        }

        throw new IllegalArgumentException("Unknown interface type: " + type);
    }
}
