```java
import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

import org.apache.commons.compress.compressors.bzip2.*;
import org.tukaani.xz.*;

public class FileCompressionUtil {

    // ---------- GZIP ----------
    public static void gzipCompress(Path source, Path target) throws IOException {
        try (InputStream in = Files.newInputStream(source);
             OutputStream out = new GZIPOutputStream(Files.newOutputStream(target))) {
            copy(in, out);
        }
    }

    public static void gzipDecompress(Path source, Path target) throws IOException {
        try (InputStream in = new GZIPInputStream(Files.newInputStream(source));
             OutputStream out = Files.newOutputStream(target)) {
            copy(in, out);
        }
    }

    // ---------- ZIP ----------
    public static void zipCompress(Path sourceDir, Path zipFile) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipFile))) {
            Files.walk(sourceDir)
                 .filter(p -> !Files.isDirectory(p))
                 .forEach(p -> {
                     ZipEntry entry = new ZipEntry(sourceDir.relativize(p).toString().replace("\\", "/"));
                     try {
                         zos.putNextEntry(entry);
                         Files.copy(p, zos);
                         zos.closeEntry();
                     } catch (IOException e) {
                         throw new UncheckedIOException(e);
                     }
                 });
        }
    }

    public static void zipDecompress(Path zipFile, Path targetDir) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFile))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path outPath = targetDir.resolve(entry.getName()).normalize();
                if (!outPath.startsWith(targetDir)) {
                    throw new IOException("Bad zip entry: " + entry.getName());
                }
                if (entry.isDirectory()) {
                    Files.createDirectories(outPath);
                } else {
                    Files.createDirectories(outPath.getParent());
                    try (OutputStream out = Files.newOutputStream(outPath)) {
                        copy(zis, out);
                    }
                }
                zis.closeEntry();
            }
        }
    }

    // ---------- BZIP2 ----------
    public static void bzip2Compress(Path source, Path target) throws IOException {
        try (InputStream in = Files.newInputStream(source);
             OutputStream out = new BZip2CompressorOutputStream(Files.newOutputStream(target))) {
            copy(in, out);
        }
    }

    public static void bzip2Decompress(Path source, Path target) throws IOException {
        try (InputStream in = new BZip2CompressorInputStream(Files.newInputStream(source));
             OutputStream out = Files.newOutputStream(target)) {
            copy(in, out);
        }
    }

    // ---------- LZMA (XZ) ----------
    public static void lzmaCompress(Path source, Path target) throws IOException {
        try (InputStream in = Files.newInputStream(source);
             OutputStream out = new XZOutputStream(Files.newOutputStream(target), new LZMA2Options())) {
            copy(in, out);
        }
    }

    public static void lzmaDecompress(Path source, Path target) throws IOException {
        try (InputStream in = new XZInputStream(Files.newInputStream(source));
             OutputStream out = Files.newOutputStream(target)) {
            copy(in, out);
        }
    }

    // ---------- Helper ----------
    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int len;
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
    }

    // ---------- Example Usage ----------
    public static void main(String[] args) throws Exception {
        // GZIP
        gzipCompress(Paths.get("example.txt"), Paths.get("example.txt.gz"));
        gzipDecompress(Paths.get("example.txt.gz"), Paths.get("example_un.gz"));

        // ZIP
        zipCompress(Paths.get("folderToZip"), Paths.get("archive.zip"));
        zipDecompress(Paths.get("archive.zip"), Paths.get("unzippedFolder"));

        // BZIP2
        bzip2Compress(Paths.get("example.txt"), Paths.get("example.txt.bz2"));
        bzip2Decompress(Paths.get("example.txt.bz2"), Paths.get("example_un.bz2"));

        // LZMA
        lzmaCompress(Paths.get("example.txt"), Paths.get("example.txt.xz"));
        lzmaDecompress(Paths.get("example.txt.xz"), Paths.get("example_un.xz"));
    }
}
```