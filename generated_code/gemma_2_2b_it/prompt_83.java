```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DigitalNotebook {
    static Map<String, List<Note>> notebooks = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Create a new notebook");
            System.out.println("2. Add a note to a notebook");
            System.out.println("3. View notes in a notebook");
            System.out.println("4. Search notes");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    createNotebook(scanner);
                    break;
                case 2:
                    addNote(scanner);
                    break;
                case 3:
                    viewNotes(scanner);
                    break;
                case 4:
                    searchNotes(scanner);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void createNotebook(Scanner scanner) {
        System.out.println("Enter notebook name:");
        String name = scanner.nextLine();
        notebooks.put(name, new ArrayList<>());
        System.out.println("Notebook created successfully!");
    }

    static void addNote(Scanner scanner) {
        System.out.println("Enter notebook name:");
        String notebookName = scanner.nextLine();

        if (!notebooks.containsKey(notebookName)) {
            System.out.println("Notebook not found. Create one first.");
            return;
        }

        System.out.println("Enter note content:");
        String content = scanner.nextLine();
        
        List<Note> notes = notebooks.get(notebookName);
        notes.add(new Note(content));
        System.out.println("Note added successfully!");
    }

    static void viewNotes(Scanner scanner) {
        System.out.println("Enter notebook name:");
        String notebookName = scanner.nextLine();

        if (!notebooks.containsKey(notebookName)) {
            System.out.println("Notebook not found. Create one first.");
            return;
        }

        List<Note> notes = notebooks.get(notebookName);
        if (notes.isEmpty()) {
            System.out.println("No notes in this notebook.");
            return;
        }

        System.out.println("Notes in " + notebookName + ":");
        for (Note note : notes) {
            System.out.println(note.getContent());
        }
    }

    static void searchNotes(Scanner scanner) {
        System.out.println("Enter search term:");
        String searchTerm = scanner.nextLine();

        for (Map.Entry<String, List<Note>> entry : notebooks.entrySet()) {
            List<Note> notes = entry.getValue();
            for (Note note : notes) {
                if (note.getContent().toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println(note.getContent());
                }
            }
        }
    }
}

class Note {
    String content;

    public Note(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
```