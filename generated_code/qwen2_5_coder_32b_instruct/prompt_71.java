```java
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class EBook {
    private String title;
    private String author;
    private boolean isLent;

    public EBook(String title, String author) {
        this.title = title;
        this.author = author;
        this.isLent = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isLent() {
        return isLent;
    }

    public void lend() {
        if (!isLent) {
            isLent = true;
        }
    }

    public void returnBook() {
        if (isLent) {
            isLent = false;
        }
    }

    @Override
    public String toString() {
        return "EBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isLent=" + isLent +
                '}';
    }
}

class VirtualLibrary {
    private List<EBook> books;

    public VirtualLibrary() {
        books = new ArrayList<>();
    }

    public void addBook(EBook book) {
        books.add(book);
    }

    public Optional<EBook> searchByTitle(String title) {
        return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).findFirst();
    }

    public Optional<EBook> searchByAuthor(String author) {
        return books.stream().filter(book -> book.getAuthor().equalsIgnoreCase(author)).findFirst();
    }

    public void lendBook(String title) {
        searchByTitle(title).ifPresent(EBook::lend);
    }

    public void returnBook(String title) {
        searchByTitle(title).ifPresent(EBook::returnBook);
    }

    public List<EBook> getAllBooks() {
        return books;
    }
}

public class Main {
    public static void main(String[] args) {
        VirtualLibrary library = new VirtualLibrary();
        library.addBook(new EBook("1984", "George Orwell"));
        library.addBook(new EBook("To Kill a Mockingbird", "Harper Lee"));

        System.out.println("All books in library:");
        library.getAllBooks().forEach(System.out::println);

        System.out.println("\nSearching for '1984':");
        library.searchByTitle("1984").ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Book not found.")
        );

        System.out.println("\nLending '1984':");
        library.lendBook("1984");

        System.out.println("\nAll books in library after lending:");
        library.getAllBooks().forEach(System.out::println);

        System.out.println("\nReturning '1984':");
        library.returnBook("1984");

        System.out.println("\nAll books in library after returning:");
        library.getAllBooks().forEach(System.out::println);
    }
}
```