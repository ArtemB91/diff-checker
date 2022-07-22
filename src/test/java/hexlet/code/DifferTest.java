package hexlet.code;

import hexlet.code.formatters.IFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @TempDir
    private Path tempDir;
    private final Path resourceDirectory = Paths.get("src", "test", "resources");

    @Test
    public void generateStylishFormatWithJSON() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        String expected =
                """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void generateStylishFormatWithYAML() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String expected =
                """
                {
                  - chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: true
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void generateIfEmptyFiles() throws IOException {

        Path file1Path = Files.createFile(tempDir.resolve("file1.json"));
        Path file2Path = Files.createFile(tempDir.resolve("file2.json"));

        String expected =
                """
                {
                }""";
        String actual = Differ.generate(file1Path.toString(), file2Path.toString());

        assertEquals(expected, actual);
    }

    @Test
    public void generatePlainFormatWithJSON() throws IOException {
        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        IFormatter formatter = Formatter.newFormatter(Formatter.FormatType.PLAIN);
        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), formatter);
        String expected =
                """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        assertEquals(expected, actual);
    }

    @Test
    public void generateJSONDiffOfJSONFiles() throws IOException {
        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        IFormatter formatter = Formatter.newFormatter(Formatter.FormatType.JSON);
        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), formatter);

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("JSONdiff.json")));
        assertEquals(expected, actual);
    }
}
