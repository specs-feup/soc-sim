package pt.up.fe.specs.socsim.model.signal.enums;

public enum Active {
    HIGH("high"),
    LOW("low");

    private final String active;

    Active(String active) {
        this.active = active;
    }

    public String getActive() {
        return this.active;
    }

    public static Active fromString(String active) {
        for (Active value : Active.values()) {
            if (value.getActive().equals(active)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Invalid active string: " + active);
    }
}
