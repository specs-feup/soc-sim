package pt.up.fe.specs.socsim.emitter;

public interface Modifier {
    String modifyToString();

    void modifyToFile(String filepath);
}
