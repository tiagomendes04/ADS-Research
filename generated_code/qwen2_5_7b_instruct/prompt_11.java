```java
import java.util.*;

class Book {
    private String title;
    private String author;
    private boolean borrowed;
    private boolean reserved;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.borrowed = false;
        this.reserved = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void borrowBook() {
        if (!borrowed && !reserved) {
            borrowed = true;
            reserved = false;
        }
    }

    public void reserveBook() {
        if (!borrowed && !reserved) {
            borrowed = false;
            reserved = true;
        }
    }

    public String toString() {
        return title + " by " + author + " - Borrowed: " + borrowed + ", Reserved: " + reserved;
    }
}

class LibraryCatalog {
    private Map<String, Book> books;

    public LibraryCatalog() {
        this.books = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getTitle(), book);
    }

    public Book getBook(String title) {
        return books.get(title);
    }

    public void borrowBook(String title) {
        Book book = books.get(title);
        if (book != null) {
            book.borrowBook();
        }
    }

    public void reserveBook(String title) {
        Book book = books.get(title);
        if (book != null) {
            book.reserveBook();
        }
    }

    public void displayCatalog() {
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        LibraryCatalog catalog = new LibraryCatalog();
        catalog.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald"));
        catalog.addBook(new Book("To Kill a Mockingbird", "Harper Lee"));
        catalog.addBook(new Book("1984", "George Orwell"));

        catalog.displayCatalog();

        catalog.borrowBook("To Kill a Mockingbird");
        catalog.reserveBook("1984");

        System.out.println("\nAfter borrow and reserve operations:");
        catalog.displayCatalog();
    }
}
```