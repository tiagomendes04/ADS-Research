```java
import java.util.*;

public class LibraryCatalog {

    private static final Map<String, Book> catalog = new HashMap<>();
    private static final Set<String> borrowedBooks = new HashSet<>();
    private static final Map<String, Queue<String>> reservations = new HashMap<>();

    public static void main(String[] args) {
        addBook("1984", "George Orwell");
        addBook("Pride and Prejudice", "Jane Austen");
        addBook("To Kill a Mockingbird", "Harper Lee");

        System.out.println("Available books: " + getAvailableBooks());

        borrowBook("1984", "Alice");
        System.out.println("Available books after borrowing: " + getAvailableBooks());

        reserveBook("To Kill a Mockingbird", "Bob");
        reserveBook("To Kill a Mockingbird", "Charlie");
        System.out.println("Reservations for 'To Kill a Mockingbird': " + reservations.get("To Kill a Mockingbird"));

        returnBook("1984");
        System.out.println("Available books after returning: " + getAvailableBooks());

        borrowBook("To Kill a Mockingbird", "Bob"); // Should be Bob because he's first in line
        System.out.println("Available books after borrowing from reservations: " + getAvailableBooks());
        System.out.println("Reservations for 'To Kill a Mockingbird': " + reservations.get("To Kill a Mockingbird"));

    }

    public static void addBook(String title, String author) {
        catalog.put(title, new Book(title, author));
    }

    public static boolean borrowBook(String title, String borrower) {
        if (catalog.containsKey(title)) {
            if (!borrowedBooks.contains(title)) {
                borrowedBooks.add(title);
                System.out.println(borrower + " has borrowed '" + title + "'.");
                return true;
            } else {
                // Check if there's a reservation and the borrower is the first in line
                Queue<String> bookReservations = reservations.get(title);
                if (bookReservations != null && !bookReservations.isEmpty() && bookReservations.peek().equals(borrower)) {
                    bookReservations.poll(); // Remove borrower from queue
                    if (bookReservations.isEmpty()) {
                        reservations.remove(title);
                    }
                    System.out.println(borrower + " has borrowed '" + title + "' from reservation.");
                    return true;
                }
                System.out.println("'" + title + "' is already borrowed.");
                return false;
            }
        } else {
            System.out.println("Book '" + title + "' not found in catalog.");
            return false;
        }
    }

    public static void returnBook(String title) {
        if (borrowedBooks.contains(title)) {
            borrowedBooks.remove(title);
            System.out.println("'" + title + "' has been returned.");
        } else {
            System.out.println("'" + title + "' was not borrowed.");
        }
    }

    public static void reserveBook(String title, String patronName) {
        reservations.computeIfAbsent(title, k -> new LinkedList<>()).offer(patronName);
        System.out.println(patronName + " has reserved '" + title + "'.");
    }

    public static Set<String> getAvailableBooks() {
        Set<String> available = new HashSet<>(catalog.keySet());
        available.removeAll(borrowedBooks);
        return available;
    }

    static class Book {
        private final String title;
        private final String author;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        @Override
        public String toString() {
            return title + " by " + author;
        }
    }
}
```