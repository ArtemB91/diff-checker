package hexlet.code;

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
    private Path resourceDirectory = Paths.get("src", "test", "resources");

    @Test
    public void testGenerateWithJSONFiles() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void testGenerateWithYAMLFiles() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String expected =
                """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void testGenerateIfEmpty() throws IOException {

        Path file1Path = Files.createFile(tempDir.resolve("file1.json"));
        Path file2Path = Files.createFile(tempDir.resolve("file2.json"));

        String expected =
                """
                {
                }""";
        String actual = Differ.generate(file1Path.toString(), file2Path.toString());

        assertEquals(expected, actual);
    }
}
