package Commands;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class with some common methods for file path resolving
 */
public class FileSystemUtility {
    /**
     * @return current working directory
     */
    public static String getCurrentWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * @param newDirectory directory, which should be new current working directory
     * @return new current working directory
     */
    public static String setCurrentWorkingDirectory(String newDirectory) {
        return System.setProperty("user.dir", newDirectory);
    }

    /**
     * @param fileName name of file in current working directory
     * @return path to provided file
     */
    public static Path resolveFilePath(String fileName) {
        return Paths.get(getCurrentWorkingDirectory()).resolve(fileName);
    }
}
