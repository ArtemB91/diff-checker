package hexlet.code;

import hexlet.code.Differ.DiffDescription.ChangeType;

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

        Path path1 = Paths.get(filepath1);
        Path path2 = Paths.get(filepath2);

        String contentFile1 = new String(Files.readAllBytes(path1));
        String contentFile2 = new String(Files.readAllBytes(path2));

        String file1Ext = Utils.getFileExtension(path1.getFileName().toString());
        String file2Ext = Utils.getFileExtension(path2.getFileName().toString());

        Map<String, String> data1 = getData(contentFile1, file1Ext);
        Map<String, String> data2 = getData(contentFile2, file2Ext);

        Map<String, DiffDescription> diff = getDiff(data1, data2);

        return diffToString(diff);
    }

    private static Map<String, String> getData(String content, String extension) throws IOException {

        if (content.isEmpty()) {
            return new HashMap<>();
        }

        Parser parser = new Parser(content, extension);
        return parser.parse();

    }



    private static Map<String, DiffDescription> getDiff(Map<String, String> data1, Map<String, String> data2) {
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

    private static String diffToString(Map<String, DiffDescription> diff) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        String strTemplate = "  %s %s: %s\n";
        for (Map.Entry<String, DiffDescription> keyAndDesc: diff.entrySet()) {
            String key = keyAndDesc.getKey();
            DiffDescription diffDesc = keyAndDesc.getValue();

            switch (diffDesc.getType()) {
                case ADDED:
                    builder.append(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case DELETED:
                    builder.append(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    break;
                case MODIFIED:
                    builder.append(String.format(strTemplate, "-", key, diffDesc.getFirstValue()));
                    builder.append(String.format(strTemplate, "+", key, diffDesc.getSecondValue()));
                    break;
                case NO_CHANGES:
                    builder.append(String.format(strTemplate, " ", key, diffDesc.getFirstValue()));
                    break;
                default:
                    throw new RuntimeException(key + " - is invalid change type");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    static class DiffDescription {
        public enum ChangeType {
            ADDED,
            DELETED,
            MODIFIED,
            NO_CHANGES
        }
        private final ChangeType type;
        private final String firstValue;
        private final String secondValue;

        public ChangeType getType() {
            return type;
        }

        public String getFirstValue() {
            return firstValue;
        }

        public String getSecondValue() {
            return secondValue;
        }

        DiffDescription(ChangeType type, String firstValue, String secondValue) {
            this.type = type;
            this.firstValue = firstValue;
            this.secondValue = secondValue;
        }

    }
}
