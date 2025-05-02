package pt.up.fe.sepcs.socsim.reader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import pt.up.fe.specs.socsim.reader.JsonReader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class JsonReaderTest {
    private final String emptyPath = "";
    private final String badPath = "/this_path_does_not_exist";
    private final String validPath1 = "/json_reader_test_1.json";
    private final String validPath2 = "/json_reader_test_2.json";

    private JsonReader reader;

    private static Stream<Arguments> getRootFields() {
        return Stream.of(
            Arguments.of("/json_reader_test_1.json", "name", "NieR Replicant"),
            Arguments.of("/json_reader_test_1.json", "year", 2010),
            Arguments.of("/json_reader_test_1.json", "peak", true),
            Arguments.of("/json_reader_test_1.json", "score", 9.9),
            Arguments.of("/json_reader_test_2.json", "name", "NieR Automata"),
            Arguments.of("/json_reader_test_2.json", "year", 2017),
            Arguments.of("/json_reader_test_2.json", "peak", true),
            Arguments.of("/json_reader_test_2.json", "score", 10.0)
        );
    }

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

    @ParameterizedTest
    @MethodSource("getRootFields")
    public void testJsonReaderFetchAtRoot(String filepath, String path, Object expected) {
        Assertions.assertDoesNotThrow(() -> this.reader = new JsonReader(filepath));

        if (expected instanceof String) {
            Assertions.assertEquals(expected, reader.getStringOrDefault(path, ""));
        } else if (expected instanceof Integer) {
            Assertions.assertEquals(expected, reader.getIntOrDefault(path, -1));
        } else if (expected instanceof Boolean) {
            Assertions.assertEquals(expected, reader.getBooleanOrDefault(path, false));
        } else if (expected instanceof Double) {
            Assertions.assertEquals((Double)expected, reader.getDoubleOrDefault(path, -1.0), 0.001);
        }
    }

    @Test
    public void testJsonReaderGetArray() {
        Assertions.assertDoesNotThrow(() -> this.reader = new JsonReader(validPath2));

        List<JsonReader> array = this.reader.getArray("characters");

        Assertions.assertFalse(array.isEmpty());
        Assertions.assertEquals(3, array.size());
    }

    @Test
    public void testJsonReaderGetArrayElement() {
        Assertions.assertDoesNotThrow(() -> this.reader = new JsonReader(validPath2));

        Optional<JsonReader> obj = this.reader.getArrayElement("characters", 0);

        Assertions.assertTrue(obj.isPresent());
    }

    @Test
    public void testJsonReaderGetObject() {
        Assertions.assertDoesNotThrow(() -> this.reader = new JsonReader(validPath2));

        Optional<JsonReader> obj = this.reader.getArrayElement("characters", 0);

        Assertions.assertTrue(obj.isPresent());

        Assertions.assertEquals("2B", obj.get().getStringOrDefault("name", ""));
        Assertions.assertEquals("YoRHa", obj.get().getStringOrDefault("org", ""));
        Assertions.assertEquals("Android", obj.get().getStringOrDefault("race", ""));
        Assertions.assertEquals("Virtuous Contract", obj.get().getStringOrDefault("weapon", ""));
        Assertions.assertEquals("Executioner", obj.get().getStringOrDefault("type", ""));
    }
}
