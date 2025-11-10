```java
import java.util.*;

public class DigitalNotebook {
    private Map<String, Note> notes;
    private Map<String, Tag> tags;

    public DigitalNotebook() {
        notes = new HashMap<>();
        tags = new HashMap<>();
    }

    public void addNote(String title, String content) {
        notes.put(title, new Note(title, content));
    }

    public void addTag(String title, String tag) {
        tags.put(title, tag);
    }

    public void search(String query) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes.values()) {
            if (note.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    note.getContent().toLowerCase().contains(query.toLowerCase())) {
                results.add(note);
            }
        }
        results.forEach(note -> System.out.println(note));
    }

    public void searchTags(String query) {
        List<Note> results = new ArrayList<>();
        for (Note note : notes.values()) {
            if (note.getTags().contains(query)) {
                results.add(note);
            }
        }
        results.forEach(note -> System.out.println(note));
    }

    public static void main(String[] args) {
        DigitalNotebook notebook = new DigitalNotebook();

        notebook.addNote("Note 1", "This is the content of note 1");
        notebook.addNote("Note 2", "This is the content of note 2");
        notebook.addTag("tag1", "This is the content of note 1");

        notebook.search("note1");
        notebook.searchTags("tag1");
    }
}
```