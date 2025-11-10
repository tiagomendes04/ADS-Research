```java
import java.io.*;
import java.util.zip.*;

public class FileCompressor {

    public static void compress(String sourceFilePath, String destinationFilePath, CompressionLevel level) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFilePath);
             FileOutputStream fos = new FileOutputStream(destinationFilePath);
             GZIPOutputStream gos = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = fis.read(buffer)) != -1) {
                gos.write(buffer, 0, read);
            }
        }
    }

    public static void decompress(String sourceFilePath, String destinationFilePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFilePath);
             FileOutputStream fos = new FileOutputStream(destinationFilePath);
             GZIPInputStream gis = new GZIPInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int read;
            while ((read = gis.read(buffer)) != -1) {
                fos.write(buffer, 0, read);
            }
        }
    }

    public static void main(String[] args) {
        try {
            String source = "test.txt";
            String destination = "test.txt.gz";
            compress(source, destination, CompressionLevel.NORMAL);
            decompress(destination, "decompressed_test.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

enum CompressionLevel {
    BEST_SPEED, BEST_COMPRESSION, NORMAL
}
```