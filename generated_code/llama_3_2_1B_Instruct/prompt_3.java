```java
import java.awt.Desktop;
import java.awt.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class FileExplorer {
    private List<File> directories = new ArrayList<>();
    private List<File> files = new ArrayList<>();
    private String currentDirectory = System.getProperty("user.dir");

    public void listDirectories(String directory) {
        File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Directories in " + directory + ":");
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                } else if (file.isDirectory()) {
                    listDirectories(file.getAbsolutePath());
                }
            }
        }
    }

    public void openFile(String filePath, String type) {
        if (type.equals("txt")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else if (type.equals("docx")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else if (type.equals("pdf")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        }
    }

    public void handleFiles(String filePath, String type) {
        if (type.equals("txt")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else if (type.equals("docx")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        } else if (type.equals("pdf")) {
            try {
                Desktop.getDesktop().open(new File(filePath));
            } catch (IOException e) {
                System.out.println("Error opening file: " + e.getMessage());
            }
        }
    }

    public void listFiles(String directory) {
        File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Files in " + directory + ":");
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    System.out.println(file.getName());
                }
            }
        }
    }

    public void openDirectory(String directory) {
        File dir = new File(directory);
        if (dir.exists() && dir.isDirectory()) {
            System.out.println("Directory in " + directory + ":");
            listFiles(directory);
            listDirectories(directory);
        }
    }

    public void run() {
        System.out.println("File Explorer");
        System.out.println("1. List directories");
        System.out.println("2. Open file");
        System.out.println("3. Handle files");
        System.out.println("4. Open directory");
        System.out.println("5. Exit");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    listDirectories(currentDirectory);
                    break;
                case 2:
                    listFiles(currentDirectory);
                    break;
                case 3:
                    handleFiles(currentDirectory, scanner.nextLine());
                    break;
                case 4:
                    openDirectory(currentDirectory);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }
}
```