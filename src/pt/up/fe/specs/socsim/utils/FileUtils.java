package pt.up.fe.specs.socsim.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtils {
    public static String read(String filepath) throws IOException {
        return Files.readString(Paths.get(filepath));
    }
}
