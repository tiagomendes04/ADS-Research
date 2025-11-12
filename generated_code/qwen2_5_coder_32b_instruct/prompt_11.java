```java
import java.util.ArrayList;
import java.util.List;

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

    public void borrowBook() {
        if (!isBorrowed && !isReserved) {
            isBorrowed = true;
        }
    }

    public void returnBook() {
        isBorrowed = false;
        isReserved = false;
    }

    public boolean isReserved() {
        return isReserved;
    }

    public void reserveBook() {
        if (!isBorrowed) {
            isReserved = true;
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
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }

    public void displayAllBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        LibraryCatalog catalog = new LibraryCatalog();

        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald");
        Book book2 = new Book("1984", "George Orwell");
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee");

        catalog.addBook(book1);
        catalog.addBook(book2);
        catalog.addBook(book3);

        book1.borrowBook();
        book2.reserveBook();

        catalog.displayAllBooks();

        List<Book> foundBooks = catalog.searchBooksByTitle("The Great Gatsby");
        System.out.println("Found Books by Title 'The Great Gatsby':");
        for (Book book : foundBooks) {
            System.out.println(book);
        }

        foundBooks = catalog.searchBooksByAuthor("George Orwell");
        System.out.println("Found Books by Author 'George Orwell':");
        for (Book book : foundBooks) {
            System.out.println(book);
        }

        book1.returnBook();
        book2.returnBook();

        catalog.displayAllBooks();
    }
}
```