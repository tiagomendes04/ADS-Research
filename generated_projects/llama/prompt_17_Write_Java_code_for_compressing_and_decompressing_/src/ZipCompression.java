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