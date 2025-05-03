package pt.up.fe.specs.socsim.model.signal;

public enum SignalType {
    LOGIC("logic"),
    REG_REQ_T("reg_req_t"),
    REG_RSP_T("reg_rsp_t"),
    OBI_REQ_T("obi_req_t"),
    OBI_RESP_T("obi_resp_t");

    private final String type;

    SignalType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static SignalType fromString(String typeString) {
        for (SignalType s : SignalType.values()) {
            if (s.type.equals(typeString)) {
                return s;
            }
        }

        throw new IllegalArgumentException("Unknown signal type: " + typeString);
    }
}
