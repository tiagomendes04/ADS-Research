```java
import java.util.*;
import java.util.stream.Collectors;

class Note {
    private String content;
    private Set<String> tags;

    public Note(String content, Set<String> tags) {
        this.content = content;
        this.tags = tags;
    }

    public String getContent() {
        return content;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "Note{" +
                "content='" + content + '\'' +
                ", tags=" + tags +
                '}';
    }
}

class DigitalNotebook {
    private List<Note> notes;

    public DigitalNotebook() {
        this.notes = new ArrayList<>();
    }

    public void addNote(String content, Set<String> tags) {
        notes.add(new Note(content, tags));
    }

    public List<Note> searchByContent(String keyword) {
        return notes.stream()
                .filter(note -> note.getContent().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Note> searchByTag(String tag) {
        return notes.stream()
                .filter(note -> note.getTags().stream().anyMatch(t -> t.equalsIgnoreCase(tag)))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        DigitalNotebook notebook = new DigitalNotebook();
        notebook.addNote("Learn Java", new HashSet<>(Arrays.asList("programming", "java")));
        notebook.addNote("Buy groceries", new HashSet<>(Arrays.asList("shopping", "daily")));
        notebook.addNote("Java Streams", new HashSet<>(Arrays.asList("programming", "java", "advanced")));

        System.out.println("Search by content 'java':");
        notebook.searchByContent("java").forEach(System.out::println);

        System.out.println("\nSearch by tag 'programming':");
        notebook.searchByTag("programming").forEach(System.out::println);
    }
}
```