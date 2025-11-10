```java
import java.io.*;
import java.util.zip.*;

public class FileCompressor {

    public static void compressFile(String inputFilePath, String outputFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(outputFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            // Add files using zip algorithm
            ZipEntry entry = new ZipEntry("sample.txt");
            zos.putNextEntry(entry);
            FileInputStream fis = new FileInputStream(inputFilePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                zos.write(buffer, 0, bytesRead);
            }
            fis.close();
            zos.closeEntry();

        }
    }

    public static void decompressFile(String inputFilePath, String outputFilePath) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(inputFilePath))) {
            ZipEntry entry = zis.getNextEntry();
            while (entry != null) {
                String fileName = entry.getName();
                // Process the file here
                File file = new File(outputFilePath + File.separator + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = zis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.close();
                zis.closeEntry();
                entry = zis.getNextEntry();
            }
        }
    }

}
```