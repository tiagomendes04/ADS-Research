```java
import java.io.*;
import java.util.*;

public class BackupSystem {
    private Map<String, List<Backup>> backups;
    private Map<String, Long> lastModified;

    public BackupSystem() {
        backups = new HashMap<>();
        lastModified = new HashMap<>();
    }

    public void addBackup(String filename, String path) {
        backups.put(filename, new ArrayList<>());
        lastModified.put(filename, System.currentTimeMillis());
    }

    public void backup(String filename) {
        if (backups.containsKey(filename)) {
            backups.get(filename).add(new Backup(filename, System.currentTimeMillis()));
            lastModified.put(filename, System.currentTimeMillis());
        }
    }

    public void restore(String filename) {
        if (backups.containsKey(filename)) {
            Backup backup = backups.get(filename).get(0);
            System.out.println("Restoring from " + backup.filename);
            // Simulate restore process (e.g., read from file, write to database)
            System.out.println("Restored " + backup.filename);
            backups.remove(filename);
        } else {
            System.out.println("No backup found for " + filename);
        }
    }

    private static class Backup {
        private String filename;
        private long timestamp;

        public Backup(String filename, long timestamp) {
            this.filename = filename;
            this.timestamp = timestamp;
        }

        public String getFilename() {
            return filename;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }
}
```