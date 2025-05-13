package pt.up.fe.specs.socsim.model.config;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record SocketOptions(Map<String, String> options) {
    public String get(String opt) { return options.get(opt); }

    public boolean has(String opt) { return options.containsKey(opt); }

    public Set<String> keys() { return options.keySet(); }
}
