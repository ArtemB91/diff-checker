package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws IOException {

        Path path1 = Paths.get(filepath1);
        Path path2 = Paths.get(filepath2);

        String contentFile1 = new String(Files.readAllBytes(path1));
        String contentFile2 = new String(Files.readAllBytes(path2));

        String file1Ext = Utils.getFileExtension(path1.getFileName().toString());
        String file2Ext = Utils.getFileExtension(path2.getFileName().toString());

        Map<String, Object> data1 = getData(contentFile1, file1Ext);
        Map<String, Object> data2 = getData(contentFile2, file2Ext);

        Map<String, Object> diff = Tree.getDiff(data1, data2);

        Formatter.FormatType formatType = Formatter.getFormatType(format);
        return Formatter.format(diff, formatType);

    }

    private static Map<String, Object> getData(String content, String extension) throws IOException {

        if (content.isEmpty()) {
            return new HashMap<>();
        }

        return Parser.parse(content, extension);
    }

}
