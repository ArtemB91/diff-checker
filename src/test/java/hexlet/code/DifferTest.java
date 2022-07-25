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
    private final Path resourceDirectory = Paths.get("src", "test", "resources");

    @Test
    public void generateDefaultFormatWithJSON() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedStylishDiffOfJSONs.txt")));
        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void generateDefaultFormatWithYAML() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedStylishDiffOfYAMLs.txt")));
        String actual = Differ.generate(filepath1.toString(), filepath2.toString());

        assertEquals(expected, actual);

    }

    @Test
    public void generateStylishFormatWithJSON() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedStylishDiffOfJSONs.txt")));
        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "stylish");

        assertEquals(expected, actual);

    }

    @Test
    public void generateStylishFormatWithYAML() throws IOException {

        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedStylishDiffOfYAMLs.txt")));
        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "stylish");

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

        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "plain");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedPlainDiffOfJSONs.txt")));
        assertEquals(expected, actual);
    }

    @Test
    public void generatePlainFormatWithYAML() throws IOException {
        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "plain");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedPlainDiffOfYAMLs.txt")));
        assertEquals(expected, actual);
    }

    @Test
    public void generateJSONFormatWithJSONs() throws IOException {
        Path filepath1 = resourceDirectory.resolve("File1.json");
        Path filepath2 = resourceDirectory.resolve("File2.json");

        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "json");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedJSONDiffOfJSONs.json")));
        assertEquals(expected, actual);
    }

    @Test
    public void generateJSONFormatWithYAMLs() throws IOException {
        Path filepath1 = resourceDirectory.resolve("File1.yml");
        Path filepath2 = resourceDirectory.resolve("File2.yml");

        String actual = Differ.generate(filepath1.toString(), filepath2.toString(), "json");

        String expected = new String(Files.readAllBytes(resourceDirectory.resolve("expectedJSONDiffOfYAMLs.json")));
        assertEquals(expected, actual);
    }
}
