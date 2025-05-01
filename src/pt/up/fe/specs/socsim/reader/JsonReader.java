package pt.up.fe.specs.socsim.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class JsonReader {
    private final JsonNode root;
    private final ObjectMapper mapper;

    public JsonReader(String filepath) throws IOException {
        this.mapper = new ObjectMapper();
        this.root = this.mapper.readTree(new File(filepath));
    }

    private Optional<JsonNode> getNode(String path) {
        if (path == null || path.isEmpty())
            return Optional.empty();

        String[] parts = path.split("\\.");
        JsonNode current = this.root;

        for (String part : parts) {
            if (current == null || current.isMissingNode())
                return Optional.empty();

            current = current.get(part);
        }

        return Optional.ofNullable(current);
    }

    public Optional<String> getString(String path) {
        return this.getNode(path)
                .filter(node -> node.getNodeType() == JsonNodeType.STRING)
                .map(JsonNode::asText);
    }

    public Optional<Integer> getInt(String path) {
        return this.getNode(path)
                .filter(JsonNode::canConvertToInt)
                .map(JsonNode::asInt);
    }

    public Optional<Boolean> getBoolean(String path) {
        return this.getNode(path)
                .filter(JsonNode::isBoolean)
                .map(JsonNode::asBoolean);
    }

    public boolean has(String path) {
        return this.getNode(path).isPresent();
    }
}
