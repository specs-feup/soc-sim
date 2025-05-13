package pt.up.fe.specs.socsim.model.config;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public record Sockopts(Map<String, Object> options) {
    public Optional<Integer> getInt(String opt) {
        Object value = options.get(opt);

        return (value instanceof Integer) ?
                Optional.of((Integer) value) :
                Optional.empty();
    }

    public Optional<String> getString(String opt) {
        Object value = options.get(opt);

        return (value instanceof String) ?
                Optional.of((String) value) :
                Optional.empty();
    }

    public boolean has(String opt) { return options.containsKey(opt); }

    public Set<String> keys() { return options.keySet(); }
}
