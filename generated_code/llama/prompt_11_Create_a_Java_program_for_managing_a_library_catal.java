```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private boolean isAvailable;
    private boolean isReserved;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        this.isReserved = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void setReserved(boolean reserved) {
        isReserved = reserved;
    }
}

class Library {
    private Map<String, Book> catalog;
    private Map<String, String> reservations;

    public Library() {
        this.catalog = new HashMap<>();
        this.reservations = new HashMap<>();
    }

    public void addBook(Book book) {
        catalog.put(book.getTitle(), book);
    }

    public void removeBook(String title) {
        catalog.remove(title);
    }

    public void borrowBook(String title, String borrower) {
        if (catalog.containsKey(title)) {
            Book book = catalog.get(title);
            if (book.isAvailable()) {
                book.setAvailable(false);
                System.out.println("Book '" + title + "' borrowed by " + borrower);
            } else {
                System.out.println("Book '" + title + "' is not available");
            }
        } else {
            System.out.println("Book '" + title + "' not found in the catalog");
        }
    }

    public void reserveBook(String title, String borrower) {
        if (catalog.containsKey(title)) {
            Book book = catalog.get(title);
            if (book.isAvailable()) {
                book.setReserved(true);
                reservations.put(title, borrower);
                System.out.println("Book '" + title + "' reserved by " + borrower);
            } else {
                System.out.println("Book '" + title + "' is not available");
            }
        } else {
            System.out.println("Book '" + title + "' not found in the catalog");
        }
    }

    public void returnBook(String title) {
        if (catalog.containsKey(title)) {
            Book book = catalog.get(title);
            if (!book.isAvailable()) {
                book.setAvailable(true);
                book.setReserved(false);
                System.out.println("Book '" + title + "' returned");
            } else {
                System.out.println("Book '" + title + "' is already available");
            }
        } else {
            System.out.println("Book '" + title + "' not found in the catalog");
        }
    }

    public void displayCatalog() {
        System.out.println("Library Catalog:");
        for (Map.Entry<String, Book> entry : catalog.entrySet()) {
            Book book = entry.getValue();
            System.out.println(entry.getKey() + " by " + book.getAuthor() + " - " + (book.isAvailable() ? "Available" : "Not Available"));
        }
    }

    public void displayReservations() {
        System.out.println("Reservations:");
        for (Map.Entry<String, String> entry : reservations.entrySet()) {
            System.out.println(entry.getKey() + " - Reserved by " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new Book("Book 1", "Author 1"));
        library.addBook(new Book("Book 2", "Author 2"));
        library.displayCatalog();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Borrow book");
            System.out.println("2. Reserve book");
            System.out.println("3. Return book");
            System.out.println("4. Display catalog");
            System.out.println("5. Display reservations");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter borrower name: ");
                    String borrower = scanner.nextLine();
                    library.borrowBook(title, borrower);
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    System.out.print("Enter borrower name: ");
                    borrower = scanner.nextLine();
                    library.reserveBook(title, borrower);
                    break;
                case 3:
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.returnBook(title);
                    break;
                case 4