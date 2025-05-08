package pt.up.fe.specs.socsim.emitter;

public interface Emitter {
    String emitToString();

    void emitToFile(String filepath);
}
