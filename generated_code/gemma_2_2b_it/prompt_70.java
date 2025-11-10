```java
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BackupSystem {

    private String backupDirectory;
    private List<File> backups;

    public BackupSystem(String backupDirectory) {
        this.backupDirectory = backupDirectory;
        this.backups = new ArrayList<>();
    }

    public void backup(File file) {
        try {
            Path filePath = Paths.get(file.getAbsolutePath());
            Path backupPath = Paths.get(backupDirectory, file.getName() + "_" + LocalDateTime.now().toString() + ".bak");
            Files.copy(filePath, backupPath);
            backups.add(backupPath.toFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void restore(String filename) {
        try {
            Path backupFile = Paths.get(backupDirectory, filename);
            Files.copy(backupFile, Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<File> getBackups() {
        return backups;
    }
}
```