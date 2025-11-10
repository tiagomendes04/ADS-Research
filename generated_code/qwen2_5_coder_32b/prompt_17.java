```java
import java.io.*;
import java.util.zip.*;

public class FileCompressorDecompressor {

    public static void compressFileGZIP(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        FileOutputStream fos = new FileOutputStream(destFilePath);
        GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            gzipOS.write(buffer, 0, bytesRead);
        }
        gzipOS.close();
        fos.close();
        fis.close();
    }

    public static void decompressFileGZIP(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        GZIPInputStream gzipIS = new GZIPInputStream(fis);
        FileOutputStream fos = new FileOutputStream(destFilePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = gzipIS.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        gzipIS.close();
        fis.close();
    }

    public static void compressFileDeflater(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        FileOutputStream fos = new FileOutputStream(destFilePath);
        DeflaterOutputStream deflaterOS = new DeflaterOutputStream(fos, new Deflater());
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            deflaterOS.write(buffer, 0, bytesRead);
        }
        deflaterOS.close();
        fos.close();
        fis.close();
    }

    public static void decompressFileInflater(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        InflaterInputStream inflaterIS = new InflaterInputStream(fis, new Inflater());
        FileOutputStream fos = new FileOutputStream(destFilePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inflaterIS.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        inflaterIS.close();
        fis.close();
    }

    public static void compressFileBzip2(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        FileOutputStream fos = new FileOutputStream(destFilePath);
        BZip2CompressorOutputStream bzip2OS = new BZip2CompressorOutputStream(fos);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bzip2OS.write(buffer, 0, bytesRead);
        }
        bzip2OS.close();
        fos.close();
        fis.close();
    }

    public static void decompressFileBzip2(String sourceFilePath, String destFilePath) throws IOException {
        FileInputStream fis = new FileInputStream(sourceFilePath);
        BZip2CompressorInputStream bzip2IS = new BZip2CompressorInputStream(fis);
        FileOutputStream fos = new FileOutputStream(destFilePath);
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bzip2IS.read(buffer)) != -1) {
            fos.write(buffer, 0, bytesRead);
        }
        fos.close();
        bzip2IS.close();
        fis.close();
    }

    public static void main(String[] args) {
        try {
            compressFileGZIP("example.txt", "example.txt.gz");
            decompressFileGZIP("example.txt.gz", "example_decompressed_gzip.txt");

            compressFileDeflater("example.txt", "example.txt.deflate");
            decompressFileInflater("example.txt.deflate", "example_decompressed_inflate.txt");

            compressFileBzip2("example.txt", "example.txt.bz2");
            decompressFileBzip2("example.txt.bz2", "example_decompressed_bzip2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
```