```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isBorrowed;
    private boolean isReserved;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
        this.isReserved = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void borrowBook() {
        if (!isBorrowed && !isReserved) {
            isBorrowed = true;
            System.out.println("Book borrowed: " + title);
        } else if (isReserved) {
            System.out.println("Book is reserved and cannot be borrowed.");
        } else {
            System.out.println("Book is already borrowed.");
        }
    }

    public void returnBook() {
        if (isBorrowed) {
            isBorrowed = false;
            System.out.println("Book returned: " + title);
        } else {
            System.out.println("Book was not borrowed.");
        }
    }

    public void reserveBook() {
        if (!isBorrowed && !isReserved) {
            isReserved = true;
            System.out.println("Book reserved: " + title);
        } else if (isBorrowed) {
            System.out.println("Book is currently borrowed and cannot be reserved.");
        } else {
            System.out.println("Book is already reserved.");
        }
    }

    public void cancelReservation() {
        if (isReserved) {
            isReserved = false;
            System.out.println("Reservation canceled for: " + title);
        } else {
            System.out.println("No reservation to cancel for: " + title);
        }
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Borrowed: " + isBorrowed + ", Reserved: " + isReserved;
    }
}

class LibraryCatalog {
    private List<Book> books;

    public LibraryCatalog() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void removeBook(String title) {
        Book bookToRemove = findBook(title);
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("Book removed: " + title);
        } else {
            System.out.println("Book not found: " + title);
        }
    }

    public void displayBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the catalog.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public Book findBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }

    public void borrowBook(String title) {
        Book book = findBook(title);
        if (book != null) {
            book.borrowBook();
        } else {
            System.out.println("Book not found: " + title);
        }
    }

    public void returnBook(String title) {
        Book book = findBook(title);
        if (book != null) {
            book.returnBook();
        } else {
            System.out.println("Book not found: " + title);
        }
    }

    public void reserveBook(String title) {
        Book book = findBook(title);
        if (book != null) {
            book.reserveBook();
        } else {
            System.out.println("Book not found: " + title);
        }
    }

    public void cancelReservation(String title) {
        Book book = findBook(title);
        if (book != null) {
            book.cancelReservation();
        } else {
            System.out.println("Book not found: " + title);
        }
    }
}

public class LibraryManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryCatalog catalog = new LibraryCatalog();

        while (true) {
            System.out.println("\nLibrary Catalog Management");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Display Books");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Reserve Book");
            System.out.println("7. Cancel Reservation");
            System.out.println("8. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();