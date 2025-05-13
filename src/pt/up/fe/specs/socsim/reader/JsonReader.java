package pt.up.fe.specs.socsim.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import pt.up.fe.specs.socsim.utils.FileUtils;

import java.io.*;
import java.net.URL;
import java.util.*;

public class JsonReader {

    private final JsonNode root;
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonReader(String filepath) throws IOException {
        this(mapper.readTree(FileUtils.read(filepath)));
    }

    public JsonReader(JsonNode root) {
        this.root = root;
    }

    private Optional<JsonNode> getNode(String path) {
        if (path == null || path.isEmpty()) return Optional.empty();

        String[] parts = path.split("\\.");
        JsonNode current = this.root;

        for (String part : parts) {
            if (current == null || current.isMissingNode()) return Optional.empty();
            current = current.get(part);
        }

        return Optional.ofNullable(current);
    }

    public Optional<String> getString(String path) {
        return getNode(path).filter(JsonNode::isTextual).map(JsonNode::asText);
    }

    public String getStringOrDefault(String path, String defaultValue) {
        return getString(path).orElse(defaultValue);
    }

    public Optional<Integer> getInt(String path) {
        return getNode(path).filter(JsonNode::isNumber).map(JsonNode::asInt);
    }

    public int getIntOrDefault(String path, int defaultValue) {
        return getInt(path).orElse(defaultValue);
    }

    public Optional<Long> getLong(String path) {
        return getNode(path).filter(JsonNode::isNumber).map(JsonNode::asLong);
    }

    public long getLongOrDefault(String path, long defaultValue) {
        return getLong(path).orElse(defaultValue);
    }

    public Optional<Double> getDouble(String path) {
        return getNode(path).filter(JsonNode::isNumber).map(JsonNode::asDouble);
    }

    public double getDoubleOrDefault(String path, double defaultValue) {
        return getDouble(path).orElse(defaultValue);
    }

    public Optional<Boolean> getBoolean(String path) {
        return getNode(path).filter(JsonNode::isBoolean).map(JsonNode::asBoolean);
    }

    public boolean getBooleanOrDefault(String path, boolean defaultValue) {
        return getBoolean(path).orElse(defaultValue);
    }

    public <E extends Enum<E>> Optional<E> getEnum(String path, Class<E> enumType) {
        return getString(path).flatMap(val -> {
            try {
                return Optional.of(Enum.valueOf(enumType, val.toUpperCase()));
            } catch (IllegalArgumentException e) {
                return Optional.empty();
            }
        });
    }

    public Optional<JsonReader> getObject(String path) {
        return getNode(path).filter(JsonNode::isObject).map(JsonReader::new);
    }

    public List<JsonReader> getArray(String path) {
        return getNode(path)
                .filter(JsonNode::isArray)
                .map(array -> {
                    List<JsonReader> list = new ArrayList<>();
                    array.forEach(elem -> list.add(new JsonReader(elem)));
                    return list;
                })
                .orElse(Collections.emptyList());
    }

    public Optional<JsonReader> getArrayElement(String path, int index) {
        return getNode(path)
                .filter(JsonNode::isArray)
                .filter(array -> index >= 0 && index < array.size())
                .map(array -> new JsonReader(array.get(index)));
    }

    public Optional<List<String>> getStringList(String path) {
        return getNode(path)
                .filter(JsonNode::isArray)
                .map(array -> {
                    List<String> list = new ArrayList<>();
                    array.forEach(elem -> {
                        if (elem.isTextual()) list.add(elem.asText());
                    });
                    return list;
                });
    }

    public boolean has(String path) {
        return getNode(path).isPresent();
    }

    public Set<String> getKeys() {
        if (!root.isObject()) return Collections.emptySet();

        Iterator<String> fieldNames = root.fieldNames();

        Set<String> keys = new HashSet<>();

        while (fieldNames.hasNext()) {
            keys.add(fieldNames.next());
        }

        return keys;
    }

    public Object get(String key) {
        JsonNode node = root.get(key);

        if (node == null || node.isNull()) return null;

        if (node.isInt()) return node.asInt();

        if (node.isLong()) return node.asLong();

        if (node.isDouble()) return node.asDouble();

        if (node.isBoolean()) return node.asBoolean();

        if (node.isTextual()) return node.asText();

        return node.toString();
    }

    public JsonNode getRootNode() {
        return root;
    }
}
