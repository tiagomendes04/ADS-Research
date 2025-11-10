```java
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileExplorer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. List directories");
            System.out.println("2. Open file");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    listDirectories(scanner);
                    break;
                case 2:
                    openFile(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void listDirectories(Scanner scanner) {
        System.out.println("Current directory:");
        File currentDirectory = new File(".");
        File[] files = currentDirectory.listFiles();

        if (files != null) {
            for (File file : files) {
                System.out.println(file.getName());
            }
        } else {
            System.out.println("Directory is empty.");
        }
    }

    static void openFile(Scanner scanner) {
        System.out.print("Enter file path: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);

        if (file.exists()) {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    System.out.print(new String(buffer, 0, bytesRead));
                }
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else {
            System.out.println("File not found.");
        }
    }
}
```