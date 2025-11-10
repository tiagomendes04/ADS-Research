```java
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public class FileBackupManager {

    private static final String BACKUP_ROOT = ".backup";
    private static final DateTimeFormatter VERSION_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public static void backupFile(Path source) throws IOException {
        if (!Files.isRegularFile(source)) throw new IllegalArgumentException("Source must be a regular file");
        Path backupDir = getBackupDir(source);
        Files.createDirectories(backupDir);
        String version = LocalDateTime.now().format(VERSION_FMT);
        Path target = backupDir.resolve(version + "_" + source.getFileName().toString());
        Files.copy(source, target, StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static List<String> listVersions(Path source) throws IOException {
        Path backupDir = getBackupDir(source);
        if (!Files.isDirectory(backupDir)) return Collections.emptyList();
        try (Stream<Path> stream = Files.list(backupDir)) {
            return stream
                    .filter(p -> p.getFileName().toString().endsWith("_" + source.getFileName().toString()))
                    .map(p -> p.getFileName().toString().split("_", 2)[0])
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        }
    }

    public static void restoreVersion(Path source, String version, Path restoreTo) throws IOException {
        Path backupDir = getBackupDir(source);
        Path backupFile = backupDir.resolve(version + "_" + source.getFileName().toString());
        if (!Files.isRegularFile(backupFile)) throw new IllegalArgumentException("Version not found");
        Files.createDirectories(restoreTo.getParent());
        Files.copy(backupFile, restoreTo, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
    }

    public static void purgeOldVersions(Path source, int keepLatest) throws IOException {
        List<String> versions = listVersions(source);
        if (versions.size() <= keepLatest) return;
        Path backupDir = getBackupDir(source);
        for (int i = keepLatest; i < versions.size(); i++) {
            String v = versions.get(i);
            Path file = backupDir.resolve(v + "_" + source.getFileName().toString());
            Files.deleteIfExists(file);
        }
    }

    private static Path getBackupDir(Path source) {
        Path relative = source.toAbsolutePath().normalize();
        Path userHome = Paths.get(System.getProperty("user.home"));
        Path backupRoot = userHome.resolve(BACKUP_ROOT);
        return backupRoot.resolve(relative.toString().replace(File.separatorChar, '_'));
    }

    // Demo usage
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Usage: java FileBackupManager <command> <file> [options]");
            System.out.println("Commands:");
            System.out.println("  backup <file>");
            System.out.println("  list <file>");
            System.out.println("  restore <file> <version> <target>");
            System.out.println("  purge <file> <keepCount>");
            return;
        }

        String cmd = args[0];
        Path file = Paths.get(args[1]);

        switch (cmd) {
            case "backup":
                backupFile(file);
                System.out.println("Backed up: " + file);
                break;
            case "list":
                List<String> versions = listVersions(file);
                versions.forEach(System.out::println);
                break;
            case "restore":
                if (args.length < 4) throw new IllegalArgumentException("restore requires <version> <target>");
                String version = args[2];
                Path target = Paths.get(args[3]);
                restoreVersion(file, version, target);
                System.out.println("Restored version " + version + " to " + target);
                break;
            case "purge":
                if (args.length < 3) throw new IllegalArgumentException("purge requires <keepCount>");
                int keep = Integer.parseInt(args[2]);
                purgeOldVersions(file, keep);
                System.out.println("Purged old versions, kept latest " + keep);
                break;
            default:
                System.out.println("Unknown command");
        }
    }
}
```