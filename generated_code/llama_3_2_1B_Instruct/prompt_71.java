```java
import java.util.*;

class Book {
    String title;
    String author;
    int id;

    public Book(String title, String author, int id) {
        this.title = title;
        this.author = author;
        this.id = id;
    }
}

class EBook {
    String title;
    String author;
    String isbn;

    public EBook(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}

class Library {
    Map<Integer, EBook> books;
    Map<String, Book> authors;
    Map<String, Book> genres;

    public Library() {
        books = new HashMap<>();
        authors = new HashMap<>();
        genres = new HashMap<>();
    }

    public void addBook(int id, EBook eBook) {
        books.put(id, eBook);
    }

    public void addAuthor(String author, String genre) {
        authors.put(author, new Book(author, "", 0));
    }

    public void addGenre(String genre) {
        genres.put(genre, new Book("", "", 0));
    }

    public EBook getBook(int id) {
        return books.get(id);
    }

    public Book getAuthor(String author) {
        return authors.get(author);
    }

    public List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        for (Book book : genres.values()) {
            if (book.author.contains(genre)) {
                books.add(book);
            }
        }
        return books;
    }

    public EBook searchBookByTitle(String title) {
        for (EBook eBook : books.values()) {
            if (eBook.title.toLowerCase().contains(title.toLowerCase())) {
                return eBook;
            }
        }
        return null;
    }

    public List<EBook> searchBooksByAuthor(String author) {
        List<EBook> books = new ArrayList<>();
        for (EBook eBook : books.values()) {
            if (eBook.author.toLowerCase().contains(author.toLowerCase())) {
                books.add(eBook);
            }
        }
        return books;
    }

    public List<EBook> searchBooksByGenre(String genre) {
        List<EBook> books = new ArrayList<>();
        for (EBook eBook : books.values()) {
            if (eBook.genre.toLowerCase().contains(genre.toLowerCase())) {
                books.add(eBook);
            }
        }
        return books;
    }

    public List<EBook> searchBooksByTitleAndAuthor(String title, String author) {
        List<EBook> books = new ArrayList<>();
        for (EBook eBook : books.values()) {
            if (eBook.title.toLowerCase().contains(title.toLowerCase()) && eBook.author.toLowerCase().contains(author.toLowerCase())) {
                books.add(eBook);
            }
        }
        return books;
    }
}

public class Main {
    public static void main(String[] args) {
        Library library = new Library();

        EBook eBook1 = new EBook("Book1", "Author1", "12345");
        EBook eBook2 = new EBook("Book2", "Author2", "67890");
        EBook eBook3 = new EBook("Book3", "Author3", "11111");

        library.addBook(1, eBook1);
        library.addBook(2, eBook2);
        library.addBook(3, eBook3);

        library.addAuthor("Author1", "Fiction");
        library.addAuthor("Author2", "Non-Fiction");
        library.addAuthor("Author3", "Fiction");

        library.addGenre("Fiction");
        library.addGenre("Non-Fiction");

        EBook result = library.searchBookByTitle("Book1");
        System.out.println(result != null ? result.title : "Book not found");

        List<Book> booksByGenre = library.getBooksByGenre("Fiction");
        for (Book book : booksByGenre) {
            System.out.println(book.title + " by " + book.author);
        }

        List<EBook> booksByAuthor = library.searchBooksByAuthor("Author1");
        for (EBook eBook : booksByAuthor) {
            System.out.println(eBook.title + " by " + eBook.author);
        }

        List<EBook> booksByGenreAndTitle = library.searchBooksByGenre("Fiction") .stream()
                .filter(eBook -> eBook.title.toLowerCase().contains("book1") && eBook.author.toLowerCase().contains("author1"))
                .collect(Collectors.toList());
        for (EBook eBook : booksByGenreAndTitle) {
            System.out.println(eBook.title + " by " + eBook.author);
        }

        List<EBook> booksByTitleAndAuthor = library.searchBooksByTitleAndAuthor("Book1", "Author1");
        for (EBook eBook : booksByTitleAndAuthor) {
            System.out.println(eBook.title + " by " + eBook.author);
        }
    }
}
```