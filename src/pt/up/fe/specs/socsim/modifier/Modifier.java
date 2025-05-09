package pt.up.fe.specs.socsim.modifier;

public interface Modifier {
    String modifyToString();

    void modifyToFile(String filepath);
}
