import java.io.*;
import java.util.zip.*;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileCompression {

    // GZIP Compression
    public static void gzipCompress(String sourceFile, String targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile);
             GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                gzipOS.write(buffer, 0, len);
            }
        }
    }

    public static void gzipDecompress(String sourceFile, String targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             GZIPInputStream gzipIS = new GZIPInputStream(fis);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIS.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        }
    }

    // Deflate Compression
    public static void deflateCompress(String sourceFile, String targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             FileOutputStream fos = new FileOutputStream(targetFile);
             DeflaterOutputStream deflateOS = new DeflaterOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                deflateOS.write(buffer, 0, len);
            }
        }
    }

    public static void deflateDecompress(String sourceFile, String targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             InflaterInputStream inflaterIS = new InflaterInputStream(fis);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inflaterIS.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        }
    }

    // ZIP Compression
    public static void zipCompress(String sourceFile, String targetFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(targetFile);
             ZipOutputStream zipOS = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(sourceFile)) {

            ZipEntry zipEntry = new ZipEntry(new File(sourceFile).getName());
            zipOS.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                zipOS.write(buffer, 0, len);
            }

            zipOS.closeEntry();
        }
    }

    public static void zipDecompress(String sourceFile, String targetFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(sourceFile);
             ZipInputStream zipIS = new ZipInputStream(fis);
             FileOutputStream fos = new FileOutputStream(targetFile)) {

            ZipEntry zipEntry = zipIS.getNextEntry();
            if (zipEntry == null) {
                return;
            }

            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipIS.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }

            zipIS.closeEntry();
        }
    }

    // Base64 Encode/Decode (Not a compression algorithm, but included as requested)
    public static void base64Encode(String sourceFile, String targetFile) throws IOException {
        Path sourcePath = Paths.get(sourceFile);
        byte[] fileContent = Files.readAllBytes(sourcePath);
        String encodedString = Base64.getEncoder().encodeToString(fileContent);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(targetFile))) {
            writer.write(encodedString);
        }
    }

    public static void base64Decode(String sourceFile, String targetFile) throws IOException {
        String encodedString;
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            encodedString = reader.readLine();
        }

        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        Path targetPath = Paths.get(targetFile);
        Files.write(targetPath, decodedBytes);
    }


    public static void main(String[] args) {
        String inputFile = "test.txt"; // Replace with your file
        String outputFileGzip = "test.txt.gz";
        String outputFileDeflate = "test.txt.deflate";
        String outputFileZip = "test.txt.zip";
        String outputFileBase64 = "test.txt.base64";
        String outputFileGzipDecompressed = "test_gzip_decompressed.txt";
        String outputFileDeflateDecompressed = "test_deflate_decompressed.txt";
        String outputFileZipDecompressed = "test_zip_decompressed.txt";
        String outputFileBase64Decompressed = "test_base64_decompressed.txt";

        // Create a sample file for testing
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("This is a test file for compression and decompression.\n");
            for (int i = 0; i < 100; i++) {
                writer.write("Line " + i + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("Compressing with GZIP...");
            gzipCompress(inputFile, outputFileGzip);
            System.out.println("Decompressing GZIP...");
            gzipDecompress(outputFileGzip, outputFileGzipDecompressed);

            System.out.println("Compressing with Deflate...");
            deflateCompress(inputFile, outputFileDeflate);
            System.out.println("Decompressing Deflate...");
            deflateDecompress(outputFileDeflate, outputFileDeflateDecompressed);

            System.out.println("Compressing with ZIP...");
            zipCompress(inputFile, outputFileZip);
            System.out.println("Decompressing ZIP...");
            zipDecompress(outputFileZip, outputFileZipDecompressed);

            System.out.println("Encoding with Base64...");
            base64Encode(inputFile, outputFileBase64);
            System.out.println("Decoding Base64...");
            base64Decode(outputFileBase64, outputFileBase64Decompressed);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}