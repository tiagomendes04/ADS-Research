```java
import java.io.File;
import java.io.IOException;

public class FileExplorer {

    public static void listDirectoriesAndFiles(File directory) {
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("Directory: " + file.getAbsolutePath());
                        listDirectoriesAndFiles(file);
                    } else {
                        System.out.println("File: " + file.getAbsolutePath() + " (" + getFileType(file) + ")");
                    }
                }
            }
        }
    }

    private static String getFileType(File file) {
        String fileName = file.getName();
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "Unknown";
    }

    public static void main(String[] args) {
        File rootDirectory = new File("/path/to/root/directory");
        if (rootDirectory.exists() && rootDirectory.isDirectory()) {
            System.out.println("Exploring directory: " + rootDirectory.getAbsolutePath());
            listDirectoriesAndFiles(rootDirectory);
        } else {
            System.out.println("Directory does not exist.");
        }
    }
}
```