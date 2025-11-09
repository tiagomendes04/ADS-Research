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