package pt.up.fe.specs.socsim.model.enums;

public enum Direction {
    INPUT("input"),
    OUTPUT("output");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return this.direction;
    }

    public static Direction fromString(String directionString) {
        for (Direction dir : Direction.values()) {
            if (dir.direction.equals(directionString)) {
                return dir;
            }
        }

        throw new IllegalArgumentException("Unknown direction: " + directionString);
    }
}
