```java
import java.util.*;

class Ebook {
    private final String isbn;
    private final String title;
    private final String author;
    private final String content;
    private int copiesTotal;
    private int copiesAvailable;

    public Ebook(String isbn, String title, String author, String content, int copies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.content = content;
        this.copiesTotal = copies;
        this.copiesAvailable = copies;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getContent() { return content; }
    public int getCopiesTotal() { return copiesTotal; }
    public int getCopiesAvailable() { return copiesAvailable; }

    public boolean lend() {
        if (copiesAvailable > 0) {
            copiesAvailable--;
            return true;
        }
        return false;
    }

    public void returnCopy() {
        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s | Title: %s | Author: %s | Available: %d/%d",
                isbn, title, author, copiesAvailable, copiesTotal);
    }
}

class Library {
    private final Map<String, Ebook> catalog = new HashMap<>();

    public void addEbook(Ebook ebook) {
        catalog.put(ebook.getIsbn(), ebook);
    }

    public Optional<Ebook> findByIsbn(String isbn) {
        return Optional.ofNullable(catalog.get(isbn));
    }

    public List<Ebook> searchByTitle(String keyword) {
        List<Ebook> result = new ArrayList<>();
        for (Ebook e : catalog.values()) {
            if (e.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public List<Ebook> searchByAuthor(String keyword) {
        List<Ebook> result = new ArrayList<>();
        for (Ebook e : catalog.values()) {
            if (e.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean lendBook(String isbn) {
        return findByIsbn(isbn).map(Ebook::lend).orElse(false);
    }

    public void returnBook(String isbn) {
        findByIsbn(isbn).ifPresent(Ebook::returnCopy);
    }

    public Collection<Ebook> listAll() {
        return catalog.values();
    }
}

public class VirtualLibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        seedData();
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1": listAllBooks(); break;
                case "2": searchBooks(); break;
                case "3": lendBook(); break;
                case "4": returnBook(); break;
                case "5": viewContent(); break;
                case "0": System.out.println("Goodbye!"); return;
                default: System.out.println("Invalid option."); break;
            }
        }
    }

    private static void seedData() {
        library.addEbook(new Ebook("978-0134685991", "Effective Java", "Joshua Bloch",
                "Content of Effective Java...", 3));
        library.addEbook(new Ebook("978-0596009205", "Head First Design Patterns", "Eric Freeman",
                "Content of Head First Design Patterns...", 2));
        library.addEbook(new Ebook("978-1491950357", "Learning Python", "Mark Lutz",
                "Content of Learning Python...", 4));
    }

    private static void printMenu() {
        System.out.println("\n=== Virtual Library ===");
        System.out.println("1. List all e‑books");
        System.out.println("2. Search e‑books");
        System.out.println("3. Lend e‑book");
        System.out.println("4. Return e‑book");
        System.out.println("5. View e‑book content");
        System.out.println("0. Exit");
        System.out.print("Select an option: ");
    }

    private static void listAllBooks() {
        System.out.println("\n--- Catalog ---");
        library.listAll().forEach(System.out::println);
    }

    private static void searchBooks() {
        System.out.print("\nSearch by (t)itle, (a)uthor, or (i)sbn? ");
        String type = scanner.nextLine().trim().toLowerCase();
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine().trim();

        List<Ebook> results = Collections.emptyList();
        switch (type) {
            case "t": results = library.searchByTitle(keyword); break;
            case "a": results = library.searchByAuthor(keyword); break;
            case "i":
                library.findByIsbn(keyword).ifPresentOrElse(
                        e -> results = List.of(e),
                        () -> results = Collections.emptyList()
                );
                break;
            default: System.out.println("Invalid search type."); return;
        }

        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            results.forEach(System.out::println);
        }
    }

    private static void lendBook() {
        System.out.print("\nEnter ISBN to lend: ");
        String isbn = scanner.nextLine().trim();
        if (library.lendBook(isbn)) {
            System.out.println("Book lent successfully.");
        } else {
            System.out.println("Unable to lend (book may not exist or no copies available).");
        }
    }

    private static void returnBook() {
        System.out.print("\nEnter ISBN to return: ");
        String isbn = scanner.nextLine().trim();
        library.returnBook(isbn);
        System.out.println("Book returned (if ISBN was valid).");
    }

    private static void viewContent() {
        System.out.print("\nEnter ISBN to view content: ");
        String isbn = scanner.nextLine().trim();
        library.findByIsbn(isbn).ifPresentOrElse(
                e -> System.out.println("\n--- Content ---\n" + e.getContent()),
                () -> System.out.println("Book not found.")
        );
    }
}
```