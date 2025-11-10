```java
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
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
}

class Library {
    private Map<String, Book> catalog;
    private Map<String, String> reservations;

    public Library() {
        catalog = new HashMap<>();
        reservations = new HashMap<>();
    }

    public void addBook(Book book) {
        catalog.put(book.getIsbn(), book);
    }

    public void borrowBook(String isbn, String borrower) {
        if (catalog.containsKey(isbn) && catalog.get(isbn).isAvailable()) {
            catalog.get(isbn).setAvailable(false);
            System.out.println("Book borrowed by " + borrower);
        } else {
            System.out.println("Book is not available for borrowing");
        }
    }

    public void reserveBook(String isbn, String borrower) {
        if (catalog.containsKey(isbn)) {
            reservations.put(isbn, borrower);
            System.out.println("Book reserved by " + borrower);
        } else {
            System.out.println("Book is not in the catalog");
        }
    }

    public void returnBook(String isbn) {
        if (catalog.containsKey(isbn) && !catalog.get(isbn).isAvailable()) {
            catalog.get(isbn).setAvailable(true);
            System.out.println("Book returned");
        } else {
            System.out.println("Book is already available or not in the catalog");
        }
    }

    public void displayCatalog() {
        System.out.println("Library Catalog:");
        for (Map.Entry<String, Book> entry : catalog.entrySet()) {
            System.out.println("Title: " + entry.getValue().getTitle() + ", Author: " + entry.getValue().getAuthor() + ", ISBN: " + entry.getValue().getIsbn());
        }
    }

    public void displayReservations() {
        System.out.println("Book Reservations:");
        for (Map.Entry<String, String> entry : reservations.entrySet()) {
            System.out.println("ISBN: " + entry.getKey() + ", Reserved by: " + entry.getValue());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Reserve Book");
            System.out.println("4. Return Book");
            System.out.println("5. Display Catalog");
            System.out.println("6. Display Reservations");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (option) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    Book book = new Book(title, author, isbn);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.print("Enter ISBN of book to borrow: ");
                    String borrowIsbn = scanner.nextLine();
                    System.out.print("Enter borrower's name: ");
                    String borrower = scanner.nextLine();
                    library.borrowBook(borrowIsbn, borrower);
                    break;
                case 3:
                    System.out.print("Enter ISBN of book to reserve: ");
                    String reserveIsbn = scanner.nextLine();
                    System.out.print("Enter borrower's name: ");
                    borrower = scanner.nextLine();
                    library.reserveBook(reserveIsbn, borrower);
                    break;
                case 4:
                    System.out.print("Enter ISBN of book to return: ");
                    String returnIsbn = scanner.nextLine();
                    library.returnBook(returnIsbn);
                    break;
                case 5:
                    library.displayCatalog();
                    break;
                case 6:
                    library.displayReservations();
                    break;
                case 7:
                    System.out.println("Exiting library management system");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
```