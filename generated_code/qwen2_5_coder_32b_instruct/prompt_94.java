```java
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class LogMonitor implements Runnable {
    private Path logFilePath;
    private Pattern alertPattern;
    private List<String> lastReadLines;

    public LogMonitor(String filePath, String regexPattern) {
        this.logFilePath = Paths.get(filePath);
        this.alertPattern = Pattern.compile(regexPattern);
        this.lastReadLines = new ArrayList<>();
    }

    @Override
    public void run() {
        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            logFilePath.getParent().register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException x) {
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }

                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path filename = ev.context();

                    if (filename.equals(logFilePath.getFileName())) {
                        checkForAlerts();
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error with file monitoring: " + e.getMessage());
        }
    }

    private void checkForAlerts() throws IOException {
        List<String> newLines = readNewLines();
        for (String line : newLines) {
            Matcher matcher = alertPattern.matcher(line);
            if (matcher.find()) {
                generateAlert(line);
            }
        }
    }

    private List<String> readNewLines() throws IOException {
        List<String> lines = Files.readAllLines(logFilePath);
        List<String> newLines = lines.subList(lastReadLines.size(), lines.size());
        lastReadLines = lines;
        return newLines;
    }

    private void generateAlert(String line) {
        System.out.println("Alert: " + line);
    }

    public static void main(String[] args) {
        String logFilePath = "/path/to/server.log";
        String alertPattern = "ERROR|WARN";

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new LogMonitor(logFilePath, alertPattern));
    }
}
```