```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class EBook {
    private String title;
    private String author;
    private String genre;
    private boolean available;

    public EBook(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void borrow() {
        this.available = false;
    }

    public void returnBook() {
        this.available = true;
    }
}

class Library {
    private Map<String, EBook> books;

    public Library() {
        this.books = new HashMap<>();
    }

    public void addBook(EBook book) {
        this.books.put(book.getTitle(), book);
    }

    public EBook getBook(String title) {
        return this.books.get(title);
    }

    public void lendBook(String title) {
        EBook book = getBook(title);
        if (book != null && book.isAvailable()) {
            book.borrow();
            System.out.println("Book lent successfully.");
        } else {
            System.out.println("Book not available for lending.");
        }
    }

    public void returnBook(String title) {
        EBook book = getBook(title);
        if (book != null && !book.isAvailable()) {
            book.returnBook();
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not available for returning.");
        }
    }

    public void searchBook(String keyword) {
        List<String> results = new ArrayList<>();
        for (String title : this.books.keySet()) {
            if (title.contains(keyword) || this.books.get(title).getAuthor().contains(keyword) || this.books.get(title).getGenre().contains(keyword)) {
                results.add(title);
            }
        }
        if (!results.isEmpty()) {
            System.out.println("Search results:");
            for (String result : results) {
                System.out.println("- " + result);
            }
        } else {
            System.out.println("No results found.");
        }
    }
}

public class VirtualLibrary {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook(new EBook("The Great Gatsby", "F. Scott Fitzgerald", "Classic"));
        library.addBook(new EBook("To Kill a Mockingbird", "Harper Lee", "Classic"));
        library.addBook(new EBook("1984", "George Orwell", "Dystopian"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Lend Book\n2. Return Book\n3. Search Book\n4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter book title to lend: ");
                    String titleToLend = scanner.nextLine();
                    library.lendBook(titleToLend);
                    break;
                case 2:
                    System.out.print("Enter book title to return: ");
                    String titleToReturn = scanner.nextLine();
                    library.returnBook(titleToReturn);
                    break;
                case 3:
                    System.out.print("Enter search keyword: ");
                    String keyword = scanner.nextLine();
                    library.searchBook(keyword);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
```