```java
import java.util.ArrayList;
import java.util.Scanner;

class Library {
    ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.borrowed = true;
                System.out.println("Book borrowed successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void reserveBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                book.reserved = true;
                System.out.println("Book reserved successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void viewBooks() {
        System.out.println("Available Books:");
        for (Book book : books) {
            if (book.reserved) {
                System.out.println(book);
            }
        }
    }
}

class Book {
    String title;
    boolean borrowed;
    boolean reserved;

    public Book(String title) {
        this.title = title;
        this.borrowed = false;
        this.reserved = false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return borrowed;
    }

    public boolean isReserved() {
        return reserved;
    }

    @Override
    public String toString() {
        return "Title: " + title + "  Borrowed: " + borrowed + " Reserved: " + reserved;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Library library = new Library();

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Borrow Book");
            System.out.println("3. Reserve Book");
            System.out.println("4. View Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Title: ");
                    String title = scanner.next();
                    Book book = new Book(title);
                    library.addBook(book);
                    break;
                case 2:
                    System.out.print("Enter Book Title: ");
                    title = scanner.next();
                    library.borrowBook(title);
                    break;
                case 3:
                    System.out.print("Enter Book Title: ");
                    title = scanner.next();
                    library.reserveBook(title);
                    break;
                case 4:
                    library.viewBooks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
```