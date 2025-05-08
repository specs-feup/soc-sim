package pt.up.fe.specs.socsim.emitter;

public interface Emitter {
    String emit();

    String emitToString();

    void emitToFile(String filepath);
}
