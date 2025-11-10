```java
import java.util.*;

class Note {
    private String title;
    private String content;
    private List<String> tags;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.tags = new ArrayList<>();
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getTags() {
        return tags;
    }
}

class DigitalNotebook {
    private List<Note> notes;

    public DigitalNotebook() {
        this.notes = new ArrayList<>();
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> searchNotes(String query) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes) {
            if (note.getTitle().contains(query) || note.getContent().contains(query) || note.getTags().contains(query)) {
                results.add(note);
            }
        }
        return results;
    }
}

public class Main {
    public static void main(String[] args) {
        DigitalNotebook notebook = new DigitalNotebook();

        Note note1 = new Note("Java Basics", "This is a note about Java programming.");
        note1.addTag("programming");
        note1.addTag("java");
        notebook.addNote(note1);

        Note note2 = new Note("Spring Framework", "Spring is a popular Java framework.");
        note2.addTag("java");
        note2.addTag("framework");
        notebook.addNote(note2);

        Note note3 = new Note("Microservices", "Microservices architecture explained.");
        note3.addTag("architecture");
        note3.addTag("microservices");
        notebook.addNote(note3);

        List<Note> searchResults = notebook.searchNotes("java");
        for (Note note : searchResults) {
            System.out.println("Title: " + note.getTitle());
            System.out.println("Tags: " + note.getTags());
            System.out.println();
        }
    }
}
```