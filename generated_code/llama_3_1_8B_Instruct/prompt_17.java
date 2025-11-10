```java
import java.io.*;
import java.util.zip.*;

public class FileCompressor {

    public static void compressGzip(String inputFile, String outputFile) throws IOException {
        GZIPOutputStream gzip = new GZIPOutputStream(new FileOutputStream(outputFile));
        File file = new File(inputFile);
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            gzip.write(buffer, 0, len);
        }
        gzip.close();
        fis.close();
    }

    public static void decompressGzip(String inputFile, String outputFile) throws IOException {
        GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(inputFile));
        File file = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzip.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        gzip.close();
        fos.close();
    }

    public static void compressZip(String inputFile, String outputFile) throws IOException {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(outputFile));
        File file = new File(inputFile);
        FileInputStream fis = new FileInputStream(file);
        zip.putNextEntry(new ZipEntry(file.getName()));
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) != -1) {
            zip.write(buffer, 0, len);
        }
        zip.closeEntry();
        fis.close();
        zip.close();
    }

    public static void decompressZip(String inputFile, String outputFile) throws IOException {
        ZipInputStream zip = new ZipInputStream(new FileInputStream(inputFile));
        File file = new File(outputFile);
        FileOutputStream fos = new FileOutputStream(file);
        ZipEntry entry;
        while ((entry = zip.getNextEntry()) != null) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zip.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.closeEntry();
        }
        zip.close();
        fos.close();
    }

    public static void compressLzma(String inputFile, String outputFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream os = new FileOutputStream(outputFile);
        LzmaOutputStream lzma = new LzmaOutputStream(os);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            lzma.write(buffer, 0, len);
        }
        bis.close();
        lzma.close();
    }

    public static void decompressLzma(String inputFile, String outputFile) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
        OutputStream os = new FileOutputStream(outputFile);
        LzmaInputStream lzma = new LzmaInputStream(bis);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = lzma.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }
        bis.close();
        lzma.close();
        os.close();
    }

    public static void main(String[] args) throws IOException {
        // Compressing and decompressing with Gzip
        FileCompressor.compressGzip("input.txt", "output.gz");
        FileCompressor.decompressGzip("output.gz", "output.txt");

        // Compressing and decompressing with Zip
        FileCompressor.compressZip("input.txt", "output.zip");
        FileCompressor.decompressZip("output.zip", "output.txt");

        // Compressing and decompressing with Lzma
        FileCompressor.compressLzma("input.txt", "output.xz");
        FileCompressor.decompressLzma("output.xz", "output.txt");
    }
}
```