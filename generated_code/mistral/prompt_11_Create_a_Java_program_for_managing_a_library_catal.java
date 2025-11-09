```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;
    private List<String> reservations;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
        this.reservations = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void addReservation(String userId) {
        if (!reservations.contains(userId)) {
            reservations.add(userId);
        }
    }

    public void removeReservation(String userId) {
        reservations.remove(userId);
    }

    public List<String> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class Library {
    private Map<String, Book> catalog;
    private Map<String, List<Book>> borrowedBooks;

    public Library() {
        this.catalog = new HashMap<>();
        this.borrowedBooks = new HashMap<>();
    }

    public void addBook(Book book) {
        catalog.put(book.getIsbn(), book);
    }

    public Book findBookByIsbn(String isbn) {
        return catalog.get(isbn);
    }

    public void borrowBook(String isbn, String userId) {
        Book book = catalog.get(isbn);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            borrowedBooks.computeIfAbsent(userId, k -> new ArrayList<>()).add(book);
            book.getReservations().clear();
        }
    }

    public void returnBook(String isbn, String userId) {
        Book book = catalog.get(isbn);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            borrowedBooks.get(userId).remove(book);
            if (!book.getReservations().isEmpty()) {
                String nextUser = book.getReservations().get(0);
                book.setAvailable(false);
                borrowedBooks.computeIfAbsent(nextUser, k -> new ArrayList<>()).add(book);
                book.removeReservation(nextUser);
            }
        }
    }

    public void reserveBook(String isbn, String userId) {
        Book book = catalog.get(isbn);
        if (book != null && !book.isAvailable()) {
            book.addReservation(userId);
        }
    }

    public void cancelReservation(String isbn, String userId) {
        Book book = catalog.get(isbn);
        if (book != null) {
            book.removeReservation(userId);
        }
    }

    public List<Book> getBorrowedBooks(String userId) {
        return borrowedBooks.getOrDefault(userId, new ArrayList<>());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(catalog.values());
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();

        // Add some books to the library
        library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "1234567890"));
        library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "0987654321"));
        library.addBook(new Book("1984", "George Orwell", "1122334455"));

        // Borrow a book
        library.borrowBook("1234567890", "user1");

        // Reserve a book
        library.reserveBook("0987654321", "user2");

        // Return a book
        library.returnBook("1234567890", "user1");

        // Print all books
        System.out.println("Library Catalog:");
        for (Book book : library.getAllBooks()) {
            System.out.println(book);
        }
    }
}
```