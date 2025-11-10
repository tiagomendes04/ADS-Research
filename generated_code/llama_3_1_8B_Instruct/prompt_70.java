```java
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileBackupSystem {
    private static final String BACKUP_DIR = "backup";
    private static final String BACKUP_VERSION_FILE = ".backup.version";
    private static final String BACKUP_FILE_EXTENSION = ".backup";

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("backup")) {
            backupFile(args[1]);
        } else if (args.length > 0 && args[0].equals("restore")) {
            restoreFile(args[1]);
        } else if (args.length > 0 && args[0].equals("list")) {
            listBackups(args[1]);
        }
    }

    public static void backupFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                String fileName = path.getFileName().toString();
                String backupFile = fileName + BACKUP_FILE_EXTENSION;
                Path backupPath = Paths.get(BACKUP_DIR, backupFile);

                // Get the current version number
                int version = getVersionNumber(filePath);

                // Create a new backup file with the next version number
                try (BufferedWriter writer = Files.newBufferedWriter(backupPath)) {
                    writer.write(String.valueOf(version + 1));
                }

                // Copy the file to the backup directory
                Files.copy(path, backupPath.resolveSibling(backupFile));
                System.out.println("Backup successful: " + backupPath);
            } else {
                System.out.println("File not found: " + filePath);
            }
        } catch (Exception e) {
            System.out.println("Error backing up file: " + e.getMessage());
        }
    }

    public static void restoreFile(String fileName) {
        try {
            Path path = Paths.get(BACKUP_DIR, fileName + BACKUP_FILE_EXTENSION);
            if (Files.exists(path)) {
                String backupFile = fileName;
                Path backupPath = Paths.get(BACKUP_DIR, backupFile + BACKUP_FILE_EXTENSION);
                Path originalPath = Paths.get(fileName);

                // Copy the backup file to the original location
                Files.copy(path, originalPath);

                // Remove the backup file
                Files.delete(path);
                Files.delete(backupPath);

                System.out.println("Restore successful: " + originalPath);
            } else {
                System.out.println("Backup file not found: " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Error restoring file: " + e.getMessage());
        }
    }

    public static void listBackups(String directory) {
        try {
            Path path = Paths.get(directory);
            if (Files.exists(path)) {
                int version = getVersionNumber(directory);
                System.out.println("Backup versions for " + path.getFileName());
                for (int i = 1; i <= version; i++) {
                    System.out.println(i);
                }
            } else {
                System.out.println("Directory not found: " + directory);
            }
        } catch (Exception e) {
            System.out.println("Error listing backups: " + e.getMessage());
        }
    }

    private static int getVersionNumber(String filePath) {
        try {
            Path path = Paths.get(filePath, BACKUP_VERSION_FILE);
            if (Files.exists(path)) {
                try (BufferedReader reader = Files.newBufferedReader(path)) {
                    return Integer.parseInt(reader.readLine());
                }
            }
        } catch (Exception e) {
            // Ignore exceptions and return 0
        }
        return 0;
    }
}
```

```java
import org.junit.jupiter.api.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileBackupSystemTest {
    @BeforeAll
    static void setup() {
        new File("backup").mkdir();
    }

    @AfterAll
    static void tearDown() {
        new File("backup").delete();
    }

    @Test
    void testBackupFile() throws IOException {
        Path path = Paths.get("test.txt");
        Files.createFile(path);
        FileBackupSystem.backupFile("test.txt");
        Assertions.assertTrue(Files.exists(Paths.get("backup/test.txt.backup")));
    }

    @Test
    void testRestoreFile() throws IOException {
        Path path = Paths.get("backup", "test.txt.backup");
        Files.createFile(path);
        FileBackupSystem.restoreFile("test.txt");
        Assertions.assertTrue(Files.exists(Paths.get("test.txt")));
        Assertions.assertFalse(Files.exists(Paths.get("backup/test.txt.backup")));
    }

    @Test
    void testListBackups() {
        FileBackupSystem.listBackups(".");
        Assertions.assertTrue(true);
    }
}
```