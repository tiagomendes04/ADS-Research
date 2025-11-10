```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Note {
    private String title;
    private String content;
    private ArrayList<String> tags;

    public Note(String title, String content, ArrayList<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = tags;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}

public class DigitalNotebook {
    private ArrayList<Note> notes;

    public DigitalNotebook() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String title, String content, ArrayList<String> tags) {
        notes.add(new Note(title, content, tags));
    }

    public void viewNote(int index) {
        if (index < notes.size()) {
            Note note = notes.get(index);
            System.out.println("Title: " + note.getTitle());
            System.out.println("Content: " + note.getContent());
            System.out.println("Tags: " + note.getTags());
        } else {
            System.out.println("Note not found.");
        }
    }

    public void deleteNote(int index) {
        if (index < notes.size()) {
            notes.remove(index);
            System.out.println("Note deleted successfully.");
        } else {
            System.out.println("Note not found.");
        }
    }

    public void searchNote(String keyword) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getTitle().toLowerCase().contains(keyword.toLowerCase()) || 
                note.getContent().toLowerCase().contains(keyword.toLowerCase()) || 
                note.getTags().toString().toLowerCase().contains(keyword.toLowerCase())) {
                viewNote(i);
                System.out.println("\n");
            }
        }
    }

    public void searchTag(String keyword) {
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getTags().toString().toLowerCase().contains(keyword.toLowerCase())) {
                viewNote(i);
                System.out.println("\n");
            }
        }
    }

    public static void main(String[] args) {
        DigitalNotebook notebook = new DigitalNotebook();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Add Note");
            System.out.println("2. View Note");
            System.out.println("3. Delete Note");
            System.out.println("4. Search Note");
            System.out.println("5. Search Tag");
            System.out.println("6. Exit");

            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.print("Enter note title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter note content: ");
                    String content = scanner.nextLine();
                    System.out.print("Enter note tags (separated by comma): ");
                    String tags = scanner.nextLine();
                    String[] tagArray = tags.split(",");
                    ArrayList<String> tagList = new ArrayList<>();
                    for (String tag : tagArray) {
                        tagList.add(tag.trim());
                    }
                    notebook.addNote(title, content, tagList);
                    break;
                case 2:
                    System.out.print("Enter note index: ");
                    int index = scanner.nextInt();
                    scanner.nextLine();
                    notebook.viewNote(index - 1);
                    break;
                case 3:
                    System.out.print("Enter note index: ");
                    int deleteIndex = scanner.nextInt();
                    scanner.nextLine();
                    notebook.deleteNote(deleteIndex - 1);
                    break;
                case 4:
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    notebook.searchNote(keyword);
                    break;
                case 5:
                    System.out.print("Enter search tag: ");
                    String tagKeyword = scanner.nextLine();
                    notebook.searchTag(tagKeyword);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
```