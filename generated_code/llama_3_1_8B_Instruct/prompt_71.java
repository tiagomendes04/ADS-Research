```java
import java.util.*;

class EBook {
    String title;
    String author;
    int pages;

    public EBook(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Pages: " + pages;
    }
}

class VirtualLibrary {
    private Map<String, EBook> eBooks;
    private Map<String, EBook> borrowedEBooks;
    private Map<String, EBook> returnedEBooks;

    public VirtualLibrary() {
        this.eBooks = new HashMap<>();
        this.borrowedEBooks = new HashMap<>();
        this.returnedEBooks = new HashMap<>();
    }

    public void addEBook(EBook eBook) {
        eBooks.put(eBook.title, eBook);
    }

    public void lendEBook(String title) {
        if (eBooks.containsKey(title)) {
            borrowedEBooks.put(title, eBooks.remove(title));
            System.out.println("EBook '" + title + "' has been lent.");
        } else {
            System.out.println("EBook '" + title + "' not found.");
        }
    }

    public void returnEBook(String title) {
        if (borrowedEBooks.containsKey(title)) {
            returnedEBooks.put(title, borrowedEBooks.remove(title));
            eBooks.put(title, returnedEBooks.remove(title));
            System.out.println("EBook '" + title + "' has been returned.");
        } else {
            System.out.println("EBook '" + title + "' not found.");
        }
    }

    public void searchEBook(String title) {
        if (eBooks.containsKey(title)) {
            System.out.println(eBooks.get(title));
        } else {
            System.out.println("EBook '" + title + "' not found.");
        }
    }

    public void displayAvailableEBooks() {
        System.out.println("Available eBooks:");
        for (EBook eBook : eBooks.values()) {
            System.out.println(eBook);
        }
    }

    public void displayBorrowedEBooks() {
        System.out.println("Borrowed eBooks:");
        for (EBook eBook : borrowedEBooks.values()) {
            System.out.println(eBook);
        }
    }

    public static void main(String[] args) {
        VirtualLibrary library = new VirtualLibrary();

        EBook eBook1 = new EBook("Book1", "Author1", 100);
        EBook eBook2 = new EBook("Book2", "Author2", 200);

        library.addEBook(eBook1);
        library.addEBook(eBook2);

        library.displayAvailableEBooks();
        library.displayBorrowedEBooks();

        library.lendEBook("Book1");
        library.lendEBook("Book2");

        library.displayAvailableEBooks();
        library.displayBorrowedEBooks();

        library.returnEBook("Book1");
        library.returnEBook("Book2");

        library.displayAvailableEBooks();
        library.displayBorrowedEBooks();

        library.searchEBook("Book1");
    }
}
```