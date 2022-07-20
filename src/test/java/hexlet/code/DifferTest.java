package hexlet.code;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {

    @TempDir
    private Path tempDir;

    @Test
    public void testGenerate() throws IOException {

        String json1 = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }
                """;
        Path file1Path = Files.write(tempDir.resolve("file1.json"), json1.getBytes());

        String json2 = """
                {
                  "timeout": 20,
                  "verbose": true,
                  "host": "hexlet.io"
                }
                """;
        Path file2Path = Files.write(tempDir.resolve("file2.json"), json2.getBytes());

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
        String actual = Differ.generate(file1Path.toString(), file2Path.toString());

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
