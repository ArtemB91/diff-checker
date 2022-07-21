package hexlet.code;

public class Utils {

    public static String getFileExtension(String filename) {

        if (filename == null) {
            return null;
        }

        int index = filename.lastIndexOf(".");
        if (index > 0) {
            return filename.substring(index + 1);
        }
        return "";
    }

}
