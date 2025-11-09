**Compression and Decompression using Java**

### Using GZIP Algorithm

```java
import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipCompression {

    public static void compressGzip(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new FileInputStream(inputFilePath);
             OutputStream os = new GZIPOutputStream(new FileOutputStream(outputFilePath))) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void decompressGzip(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new GZIPInputStream(new FileInputStream(inputFilePath));
             OutputStream os = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.gz";

        compressGzip(inputFilePath, outputFilePath);
        System.out.println("Compressed file created: " + outputFilePath);

        decompressGzip(outputFilePath, "decompressed.txt");
        System.out.println("Decompressed file created: decompressed.txt");
    }
}
```

### Using ZIP Algorithm

```java
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipCompression {

    public static void compressZip(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new FileInputStream(inputFilePath);
             OutputStream os = new ZipOutputStream(new FileOutputStream(outputFilePath))) {

            ZipEntry entry = new ZipEntry("input.txt");
            os.putNextEntry(entry);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.closeEntry();
        }
    }

    public static void decompressZip(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new ZipInputStream(new FileInputStream(inputFilePath));
             OutputStream os = new FileOutputStream(outputFilePath)) {

            ZipEntry entry;
            while ((entry = is.getNextEntry()) != null) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.zip";

        compressZip(inputFilePath, outputFilePath);
        System.out.println("Compressed file created: " + outputFilePath);

        decompressZip(outputFilePath, "decompressed.txt");
        System.out.println("Decompressed file created: decompressed.txt");
    }
}
```

### Using LZW Algorithm

```java
import java.io.*;
import java.util.*;

public class LzwCompression {

    private static final int MAX_CODE = 4096;
    private static final int START_CODE = 256;

    public static void compressLzw(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new FileInputStream(inputFilePath);
             OutputStream os = new FileOutputStream(outputFilePath)) {

            Map<String, Integer> dictionary = new HashMap<>();
            for (int i = START_CODE; i < MAX_CODE; i++) {
                dictionary.put(String.valueOf((char) i), i);
            }

            int code = START_CODE;
            StringBuilder sb = new StringBuilder();
            int bytesRead;
            while ((bytesRead = is.read()) != -1) {
                char c = (char) bytesRead;
                String s = sb.toString() + c;
                if (dictionary.containsKey(s)) {
                    sb = new StringBuilder(s);
                } else {
                    os.write(dictionary.get(sb.toString()));
                    dictionary.put(s, code++);
                    sb = new StringBuilder(String.valueOf(c));
                }
            }
            os.write(dictionary.get(sb.toString()));
        }
    }

    public static void decompressLzw(String inputFilePath, String outputFilePath) throws IOException {
        try (InputStream is = new FileInputStream(inputFilePath);
             OutputStream os = new FileOutputStream(outputFilePath)) {

            Map<Integer, String> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put(i, String.valueOf((char) i));
            }

            int code = 256;
            int prevCode = 0;
            int bytesRead;
           