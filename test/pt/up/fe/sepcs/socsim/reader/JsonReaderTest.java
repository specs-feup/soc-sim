package pt.up.fe.sepcs.socsim.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pt.up.fe.specs.socsim.reader.JsonReader;

public class JsonReaderTest {
    private final String emptyPath = "";
    private final String badPath = "/this_path_does_not_exist";
    private final String validPath1 = "/json_reader_test_1.json";
    private final String validPath2 = "/json_reader_test_2.json";

    private JsonReader reader;

    @ParameterizedTest
    @ValueSource(strings = {validPath1, validPath2})
    public void testJsonReaderConstructorWithValidPath(String path) {
        Assertions.assertDoesNotThrow(() -> this.reader = new JsonReader(path));
    }

    @ParameterizedTest
    @ValueSource(strings = {badPath, emptyPath})
    public void testJsonReaderConstructorWithInvalidPath(String path) {
        Assertions.assertThrows(Exception.class, () -> this.reader = new JsonReader(path));
    }
}
