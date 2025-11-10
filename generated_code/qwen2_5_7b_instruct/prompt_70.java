```java
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileBackupSystem {

    private static final String BACKUP_DIR = "backups/";
    private static final String VERSION_DIR = "versions/";

    public static void backupFile(String sourceFile, String version) throws IOException {
        String backupDir = BACKUP_DIR + version + "/";
        Files.createDirectories(Paths.get(backupDir));
        File source = new File(sourceFile);
        String backupFile = backupDir + source.getName();
        Files.copy(source.toPath(), Paths.get(backupFile), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File backed up: " + sourceFile + " to " + backupFile);
    }

    public static void createVersion(String version) throws IOException {
        String versionDir = BACKUP_DIR + version + "/";
        Files.createDirectories(Paths.get(versionDir));
        System.out.println("Version " + version + " created.");
    }

    public static void createBackup(String sourceFile) throws IOException {
        String latestVersion = getLatestVersion();
        if (latestVersion == null) {
            createVersion("v1");
            backupFile(sourceFile, "v1");
        } else {
            createVersion(latestVersion + 1);
            backupFile(sourceFile, latestVersion + 1);
        }
    }

    public static void restoreFile(String sourceFile, String version) throws IOException {
        String backupDir = BACKUP_DIR + version + "/";
        File[] backups = new File(backupDir).listFiles();
        if (backups == null || backups.length == 0) {
            System.out.println("No backups found for version: " + version);
            return;
        }
        File latestBackup = Arrays.stream(backups).max(Comparator.comparingLong(File::lastModified)).orElse(null);
        if (latestBackup == null) {
            System.out.println("No valid backups found for version: " + version);
            return;
        }
        File destFile = new File(sourceFile);
        Files.copy(latestBackup.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File restored: " + sourceFile + " from version " + version);
    }

    public static String getLatestVersion() {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return null;
        }
        File[] versions = backupDir.listFiles((dir, name) -> name.startsWith("v"));
        if (versions == null || versions.length == 0) {
            return null;
        }
        Arrays.sort(versions, Comparator.comparingInt(version -> Integer.parseInt(version.getName().substring(1))));
        return versions[versions.length - 1].getName();
    }

    public static void main(String[] args) {
        try {
            createBackup("example.txt");
            restoreFile("example.txt", "v1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```