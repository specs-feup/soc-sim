package pt.up.fe.specs.socsim.model.signal.enums;

public enum Role {
    REQ("request"),
    REP("response");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() { return this.role; }

    public static Role fromString(String roleString) {
        for (Role r : Role.values())
            if (r.role.equals(roleString))
                return r;

        throw new IllegalArgumentException("Unknown role: " + roleString);
    }
}
