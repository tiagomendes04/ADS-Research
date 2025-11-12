```java
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileBackupSystem {
    private static final String BACKUP_DIR = "backups";
    private static final String VERSION_FILE = "versions.txt";

    public void backupFile(String filePath) throws IOException {
        Path sourcePath = Paths.get(filePath);
        if (!Files.exists(sourcePath)) return;

        Files.createDirectories(Paths.get(BACKUP_DIR));

        String fileName = sourcePath.getFileName().toString();
        String versionedFileName = generateVersionedFileName(fileName);

        Files.copy(sourcePath, Paths.get(BACKUP_DIR, versionedFileName), StandardCopyOption.REPLACE_EXISTING);
        updateVersionFile(fileName, versionedFileName);
    }

    private String generateVersionedFileName(String fileName) {
        int version = 1;
        File versionFile = new File(BACKUP_DIR, VERSION_FILE);
        if (versionFile.exists()) {
            try (Scanner scanner = new Scanner(versionFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(fileName)) {
                        String[] parts = line.split("=");
                        version = Integer.parseInt(parts[1]) + 1;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return fileName + "_v" + version;
    }

    private void updateVersionFile(String fileName, String versionedFileName) throws IOException {
        FileWriter writer = new FileWriter(new File(BACKUP_DIR, VERSION_FILE), true);
        writer.write(fileName + "=" + versionedFileName.replace(fileName + "_v", "") + "\n");
        writer.close();
    }

    public void restoreFile(String filePath, int version) throws IOException {
        Path sourcePath = Paths.get(filePath);
        String fileName = sourcePath.getFileName().toString();

        File versionFile = new File(BACKUP_DIR, VERSION_FILE);
        String versionedFileName = null;
        if (versionFile.exists()) {
            try (Scanner scanner = new Scanner(versionFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(fileName)) {
                        String[] parts = line.split("=");
                        int fileVersion = Integer.parseInt(parts[1]);
                        if (fileVersion == version) {
                            versionedFileName = parts[0] + "_v" + parts[1];
                            break;
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if (versionedFileName != null) {
            Path backupPath = Paths.get(BACKUP_DIR, versionedFileName);
            if (Files.exists(backupPath)) {
                Files.copy(backupPath, sourcePath, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public List<String> listBackups(String fileName) {
        List<String> backups = new ArrayList<>();
        File versionFile = new File(BACKUP_DIR, VERSION_FILE);
        if (versionFile.exists()) {
            try (Scanner scanner = new Scanner(versionFile)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(fileName)) {
                        backups.add(line);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return backups;
    }

    public static void main(String[] args) {
        FileBackupSystem system = new FileBackupSystem();
        try {
            system.backupFile("example.txt");
            system.restoreFile("example.txt", 1);
            List<String> backups = system.listBackups("example.txt");
            for (String backup : backups) {
                System.out.println(backup);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```