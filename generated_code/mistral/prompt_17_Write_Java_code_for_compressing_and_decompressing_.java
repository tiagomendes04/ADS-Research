Here's the Java code for compressing and decompressing files using different algorithms (GZIP, ZIP, and LZW):

```java
import java.io.*;
import java.util.zip.*;
import java.util.*;

public class FileCompression {

    // GZIP Compression
    public static void gzipCompress(String inputFile, String outputFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             GZIPOutputStream gzos = new GZIPOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzos.write(buffer, 0, len);
            }
        }
    }

    // GZIP Decompression
    public static void gzipDecompress(String inputFile, String outputFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             GZIPInputStream gzis = new GZIPInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        }
    }

    // ZIP Compression
    public static void zipCompress(String inputFile, String outputFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputFile);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            File fileToZip = new File(inputFile);
            try (FileInputStream fis = new FileInputStream(fileToZip)) {
                ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                zos.putNextEntry(zipEntry);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
            }
        }
    }

    // ZIP Decompression
    public static void zipDecompress(String inputFile, String outputDir) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFile);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File newFile = new File(outputDir + File.separator + fileName);
                newFile.getParentFile().mkdirs();
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                }
            }
        }
    }

    // LZW Compression
    public static void lzwCompress(String inputFile, String outputFile) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile))) {
            int bits = 12;
            int maxCode = (1 << bits) - 1;
            int nextCode = 256;
            Map<String, Integer> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put("" + (char) i, i);
            }

            String current = "";
            int b;
            while ((b = dis.read()) != -1) {
                char c = (char) b;
                String entry = current + c;
                if (dictionary.containsKey(entry)) {
                    current = entry;
                } else {
                    dos.writeInt(dictionary.get(current));
                    if (nextCode < maxCode) {
                        dictionary.put(entry, nextCode++);
                    }
                    current = "" + c;
                }
            }
            if (!current.isEmpty()) {
                dos.writeInt(dictionary.get(current));
            }
        }
    }

    // LZW Decompression
    public static void lzwDecompress(String inputFile, String outputFile) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile));
             DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputFile))) {
            int bits = 12;
            int maxCode = (1 << bits) - 1;
            int nextCode = 256;
            Map<Integer, String> dictionary = new HashMap<>();
            for (int i = 0; i < 256; i++) {
                dictionary.put(i, "" + (char) i);
            }

            String previous = "" + (char) dis.readInt();
            dos.writeBytes(previous);
            int code;
            while ((code = dis.readInt()) != -1) {
                String entry;
                if (dictionary.containsKey(code)) {
                    entry = dictionary.get(code);
                } else if (code == nextCode) {
                    entry = previous + previous.charAt(0);
                } else {
                    throw new IOException("Invalid compressed file");
                }
                dos.writeBytes(entry);
                if (nextCode <= maxCode) {
                    dictionary.put(nextCode++, previous + entry.charAt(0));
                }
                previous = entry;
            }
        }
    }

    public static void main(String[] args) {
        try {
            // Example usage
            String inputFile = "input.txt";
            String gzipOutput = "output.gz";
            String gzipDecompressed = "output_decompressed.txt";
            String zipOutput = "output.zip";
            String zipOutputDir = "output_dir";
            String lzwOutput = "output.lzw";
            String lzwDecompressed = "output_lzw_decompressed.txt";

            // GZIP
            gzipCompress(inputFile, gzipOutput);
            gzipDecompress(gzipOutput, gzipDecompressed);

            // ZIP
            zipCompress(inputFile, zipOutput);
            zipDecompress(zipOutput, zipOutputDir);

            // LZW
            lzwCompress(inputFile, lzwOutput);
            lzwDecompress(lzwOutput, lzwDecompressed);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```