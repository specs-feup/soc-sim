package pt.up.fe.specs.socsim.model.clock;

public enum Edge {
    POS("pos"),
    NEG("neg");

    private final String edge;

    Edge(String edge) {
        this.edge = edge;
    }

    public String getEdge() {
        return this.edge;
    }

    public static Edge fromString(String edgeString) {
        for (Edge edge : Edge.values()) {
            if (edge.getEdge().equals(edgeString)) {
                return edge;
            }
        }

        throw new IllegalArgumentException("Unknown edge: " + edgeString);
    }
}
