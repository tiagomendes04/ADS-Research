```java
import java.util.*;

class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private boolean isBorrowed;
    private final Queue<String> reservationQueue = new LinkedList<>();

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isBorrowed() { return isBorrowed; }

    public boolean borrow(String borrower) {
        if (isBorrowed) return false;
        if (!reservationQueue.isEmpty() && !reservationQueue.peek().equals(borrower))
            return false;
        if (!reservationQueue.isEmpty()) reservationQueue.poll();
        isBorrowed = true;
        return true;
    }

    public void returnBook() {
        isBorrowed = false;
    }

    public void reserve(String reserver) {
        if (!reservationQueue.contains(reserver))
            reservationQueue.add(reserver);
    }

    public String nextReservation() {
        return reservationQueue.peek();
    }

    @Override
    public String toString() {
        return String.format("%s - \"%s\" by %s [%s]%s",
                isbn, title, author,
                isBorrowed ? "Borrowed" : "Available",
                reservationQueue.isEmpty() ? "" : " | Reserved by: " + reservationQueue);
    }
}

class LibraryCatalog {
    private final Map<String, Book> books = new HashMap<>();

    public void addBook(Book b) {
        books.put(b.getIsbn(), b);
    }

    public Book findByIsbn(String isbn) {
        return books.get(isbn);
    }

    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values())
            if (b.getTitle().toLowerCase().contains(title.toLowerCase()))
                result.add(b);
        return result;
    }

    public List<Book> listAll() {
        return new ArrayList<>(books.values());
    }
}

public class LibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final LibraryCatalog catalog = new LibraryCatalog();

    public static void main(String[] args) {
        seedData();
        while (true) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listAllBooks();
                case "2" -> searchBooks();
                case "3" -> borrowBook();
                case "4" -> returnBook();
                case "5" -> reserveBook();
                case "6" -> System.exit(0);
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void seedData() {
        catalog.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
        catalog.addBook(new Book("978-0596009205", "Head First Java", "Kathy Sierra"));
        catalog.addBook(new Book("978-0201633610", "Design Patterns", "Erich Gamma"));
    }

    private static void showMenu() {
        System.out.println("\n=== Library Catalog ===");
        System.out.println("1. List all books");
        System.out.println("2. Search by title");
        System.out.println("3. Borrow a book");
        System.out.println("4. Return a book");
        System.out.println("5. Reserve a book");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    private static void listAllBooks() {
        System.out.println("\n--- All Books ---");
        for (Book b : catalog.listAll())
            System.out.println(b);
    }

    private static void searchBooks() {
        System.out.print("\nEnter title keyword: ");
        String kw = scanner.nextLine();
        List<Book> results = catalog.searchByTitle(kw);
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            System.out.println("\n--- Search Results ---");
            for (Book b : results) System.out.println(b);
        }
    }

    private static void borrowBook() {
        System.out.print("\nEnter ISBN to borrow: ");
        String isbn = scanner.nextLine();
        Book b = catalog.findByIsbn(isbn);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        if (b.borrow(name)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Cannot borrow. Book is either borrowed or reserved by someone else.");
        }
    }

    private static void returnBook() {
        System.out.print("\nEnter ISBN to return: ");
        String isbn = scanner.nextLine();
        Book b = catalog.findByIsbn(isbn);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        if (!b.isBorrowed()) {
            System.out.println("Book is not currently borrowed.");
            return;
        }
        b.returnBook();
        System.out.println("Book returned.");
        if (b.nextReservation() != null) {
            System.out.println("Next reservation holder: " + b.nextReservation());
        }
    }

    private static void reserveBook() {
        System.out.print("\nEnter ISBN to reserve: ");
        String isbn = scanner.nextLine();
        Book b = catalog.findByIsbn(isbn);
        if (b == null) {
            System.out.println("Book not found.");
            return;
        }
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        b.reserve(name);
        System.out.println("Reservation placed.");
    }
}
```