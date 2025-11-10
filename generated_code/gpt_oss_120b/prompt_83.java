```java
import java.util.*;
import java.time.*;

class Note {
    private final UUID id;
    private String title;
    private String content;
    private final Set<String> tags;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Note(String title, String content, Collection<String> tags) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.tags = new HashSet<>(tags);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    public UUID getId() { return id; }

    public String getTitle() { return title; }

    public String getContent() { return content; }

    public Set<String> getTags() { return Collections.unmodifiableSet(tags); }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setTitle(String title) {
        this.title = title;
        touch();
    }

    public void setContent(String content) {
        this.content = content;
        touch();
    }

    public void addTag(String tag) {
        tags.add(tag);
        touch();
    }

    public void removeTag(String tag) {
        tags.remove(tag);
        touch();
    }

    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return String.format(
                "ID: %s%nTitle: %s%nContent: %s%nTags: %s%nCreated: %s%nUpdated: %s%n",
                id, title, content, String.join(", ", tags),
                createdAt, updatedAt);
    }
}

class Notebook {
    private final List<Note> notes = new ArrayList<>();

    public void addNote(Note note) {
        notes.add(note);
    }

    public List<Note> getAllNotes() {
        return Collections.unmodifiableList(notes);
    }

    public Optional<Note> getNoteById(UUID id) {
        return notes.stream().filter(n -> n.getId().equals(id)).findFirst();
    }

    public List<Note> search(String query) {
        String lower = query.toLowerCase();
        List<Note> result = new ArrayList<>();
        for (Note n : notes) {
            if (n.getTitle().toLowerCase().contains(lower) ||
                n.getContent().toLowerCase().contains(lower) ||
                n.getTags().stream().anyMatch(t -> t.toLowerCase().contains(lower))) {
                result.add(n);
            }
        }
        return result;
    }

    public boolean deleteNote(UUID id) {
        return notes.removeIf(n -> n.getId().equals(id));
    }
}

public class DigitalNotebookApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final Notebook NOTEBOOK = new Notebook();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            String choice = SCANNER.nextLine().trim();
            switch (choice) {
                case "1" -> createNote();
                case "2" -> listNotes();
                case "3" -> viewNote();
                case "4" -> editNote();
                case "5" -> deleteNote();
                case "6" -> searchNotes();
                case "0" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Digital Notebook ===");
        System.out.println("1. Create note");
        System.out.println("2. List all notes");
        System.out.println("3. View note");
        System.out.println("4. Edit note");
        System.out.println("5. Delete note");
        System.out.println("6. Search notes");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void createNote() {
        System.out.print("Title: ");
        String title = SCANNER.nextLine().trim();
        System.out.print("Content: ");
        String content = SCANNER.nextLine().trim();
        System.out.print("Tags (comma separated): ");
        String tagLine = SCANNER.nextLine().trim();
        List<String> tags = tagLine.isEmpty() ? List.of() :
                Arrays.stream(tagLine.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .toList();
        Note note = new Note(title, content, tags);
        NOTEBOOK.addNote(note);
        System.out.println("Note created with ID: " + note.getId());
    }

    private static void listNotes() {
        List<Note> notes = NOTEBOOK.getAllNotes();
        if (notes.isEmpty()) {
            System.out.println("No notes available.");
            return;
        }
        for (Note n : notes) {
            System.out.println("- ID: " + n.getId() + " | Title: " + n.getTitle());
        }
    }

    private static void viewNote() {
        Note note = promptForNote();
        if (note != null) {
            System.out.println("\n--- Note Details ---");
            System.out.println(note);
        }
    }

    private static void editNote() {
        Note note = promptForNote();
        if (note == null) return;

        System.out.print("New title (leave blank to keep): ");
        String title = SCANNER.nextLine().trim();
        if (!title.isEmpty()) note.setTitle(title);

        System.out.print("New content (leave blank to keep): ");
        String content = SCANNER.nextLine().trim();
        if (!content.isEmpty()) note.setContent(content);

        System.out.print("Add tags (comma separated, leave blank to skip): ");
        String addTags = SCANNER.nextLine().trim();
        if (!addTags.isEmpty()) {
            Arrays.stream(addTags.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(note::addTag);
        }

        System.out.print("Remove tags (comma separated, leave blank to skip): ");
        String removeTags = SCANNER.nextLine().trim();
        if (!removeTags.isEmpty()) {
            Arrays.stream(removeTags.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .forEach(note::removeTag);
        }

        System.out.println("Note updated.");
    }

    private static void deleteNote() {
        Note note = promptForNote();
        if (note == null) return;
        if (NOTEBOOK.deleteNote(note.getId())) {
            System.out.println("Note deleted.");
        } else {
            System.out.println("Failed to delete note.");
        }
    }

    private static void searchNotes() {
        System.out.print("Search query: ");
        String query = SCANNER.nextLine().trim();
        List<Note> results = NOTEBOOK.search(query);
        if (results.isEmpty()) {
            System.out.println("No matching notes found.");
            return;
        }
        System.out.println("\n--- Search Results ---");
        for (Note n : results) {
            System.out.println("- ID: " + n.getId() + " | Title: " + n.getTitle());
        }
    }

    private static Note promptForNote() {
        System.out.print("Enter note ID: ");
        String idStr = SCANNER.nextLine().trim();
        try {
            UUID id = UUID.fromString(idStr);
            return NOTEBOOK.getNoteById(id).orElse(null);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid UUID format.");
            return null;
        }
    }
}
```