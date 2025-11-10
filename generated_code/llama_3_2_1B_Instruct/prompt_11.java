```java
import java.util.ArrayList;
import java.util.List;

class Book {
    String title;
    String author;
    int id;

    public Book(String title, String author, int id) {
        this.title = title;
        this.author = author;
        this.id = id;
    }

    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ID: " + id;
    }
}

class Library {
    List<Book> books;
    String catalogName;

    public Library(String catalogName) {
        this.books = new ArrayList<>();
        this.catalogName = catalogName;
    }

    public void borrowBook(String title, String author, int id) {
        Book book = new Book(title, author, id);
        books.add(book);
    }

    public void reserveBook(String title, String author, int id) {
        Book book = new Book(title, author, id);
        books.add(book);
    }

    public void displayCatalog() {
        for (Book book : books) {
            System.out.println(book.toString());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library("My Library");

        Book book1 = new Book("Book 1", "Author 1", 1);
        Book book2 = new Book("Book 2", "Author 2", 2);

        library.borrowBook("Book 1", "Author 1", 1);
        library.borrowBook("Book 2", "Author 2", 2);

        library.reserveBook("Book 1", "Author 1", 1);
        library.reserveBook("Book 2", "Author 2", 2);

        library.displayCatalog();
    }
}
```