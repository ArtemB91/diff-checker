package hexlet.code;

import hexlet.code.Differ.DiffDescription.ChangeType;
import hexlet.code.formatters.IFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, Formatter.newFormatter(Formatter.FormatType.STYLISH));
    }

    public static String generate(String filepath1, String filepath2, IFormatter formatter) throws IOException {

        Path path1 = Paths.get(filepath1);
        Path path2 = Paths.get(filepath2);

        String contentFile1 = new String(Files.readAllBytes(path1));
        String contentFile2 = new String(Files.readAllBytes(path2));

        String file1Ext = Utils.getFileExtension(path1.getFileName().toString());
        String file2Ext = Utils.getFileExtension(path2.getFileName().toString());

        Map<String, Object> data1 = getData(contentFile1, file1Ext);
        Map<String, Object> data2 = getData(contentFile2, file2Ext);

        Map<String, DiffDescription> diff = getDiff(data1, data2);

        return formatter.format(diff);

    }

    private static Map<String, Object> getData(String content, String extension) throws IOException {

        if (content.isEmpty()) {
            return new HashMap<>();
        }

        Parser parser = new Parser(extension);
        return parser.parse(content);
    }

    private static Map<String, DiffDescription> getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        Set<String> keySet = new HashSet<>();
        keySet.addAll(data1.keySet());
        keySet.addAll(data2.keySet());

        Map<String, DiffDescription> diff = new TreeMap<>();

        for (String key : keySet) {

            if (data1.containsKey(key) && !data2.containsKey(key)) {
                diff.put(key, new DiffDescription(ChangeType.DELETED, data1.get(key), null));
                continue;
            }

            if (!data1.containsKey(key) && data2.containsKey(key)) {
                diff.put(key, new DiffDescription(ChangeType.ADDED, null, data2.get(key)));
                continue;
            }

            if (Objects.equals(data1.get(key), data2.get(key))) {
                diff.put(key, new DiffDescription(ChangeType.NO_CHANGES, data1.get(key), data2.get(key)));
            } else {
                diff.put(key, new DiffDescription(ChangeType.MODIFIED, data1.get(key), data2.get(key)));
            }

        }
        return diff;
    }

    public static final class DiffDescription {
        public enum ChangeType {
            ADDED,
            DELETED,
            MODIFIED,
            NO_CHANGES
        }
        private final ChangeType type;
        private final Object firstValue;
        private final Object secondValue;

        public ChangeType getType() {
            return type;
        }

        public Object getFirstValue() {
            return firstValue;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        DiffDescription(ChangeType type, Object firstValue, Object secondValue) {
            this.type = type;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

    }
}
