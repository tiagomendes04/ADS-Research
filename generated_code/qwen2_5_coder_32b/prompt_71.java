```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EBook {
    private String title;
    private String author;
    private boolean isAvailable;

    public EBook(String title, String author) {
        this.title = title;
        this.author = author;
        this.isAvailable = true;
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

    @Override
    public String toString() {
        return "EBook{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}

class VirtualLibrary {
    private List<EBook> eBooks;

    public VirtualLibrary() {
        eBooks = new ArrayList<>();
    }

    public void addEBook(EBook eBook) {
        eBooks.add(eBook);
    }

    public void lendEBook(String title) {
        for (EBook eBook : eBooks) {
            if (eBook.getTitle().equalsIgnoreCase(title) && eBook.isAvailable()) {
                eBook.setAvailable(false);
                System.out.println("Lended: " + eBook);
                return;
            }
        }
        System.out.println("EBook not available or not found.");
    }

    public void returnEBook(String title) {
        for (EBook eBook : eBooks) {
            if (eBook.getTitle().equalsIgnoreCase(title) && !eBook.isAvailable()) {
                eBook.setAvailable(true);
                System.out.println("Returned: " + eBook);
                return;
            }
        }
        System.out.println("EBook not lent or not found.");
    }

    public List<EBook> searchByTitle(String title) {
        List<EBook> results = new ArrayList<>();
        for (EBook eBook : eBooks) {
            if (eBook.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(eBook);
            }
        }
        return results;
    }

    public List<EBook> searchByAuthor(String author) {
        List<EBook> results = new ArrayList<>();
        for (EBook eBook : eBooks) {
            if (eBook.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                results.add(eBook);
            }
        }
        return results;
    }

    public void displayAllEBooks() {
        for (EBook eBook : eBooks) {
            System.out.println(eBook);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        VirtualLibrary library = new VirtualLibrary();
        library.addEBook(new EBook("Java Programming", "John Doe"));
        library.addEBook(new EBook("Effective Java", "Joshua Bloch"));
        library.addEBook(new EBook("Head First Java", "Kathy Sierra"));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Display all EBooks");
            System.out.println("2. Lend an EBook");
            System.out.println("3. Return an EBook");
            System.out.println("4. Search by Title");
            System.out.println("5. Search by Author");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    library.displayAllEBooks();
                    break;
                case 2:
                    System.out.print("Enter the title of the EBook to lend: ");
                    String lendTitle = scanner.nextLine();
                    library.lendEBook(lendTitle);
                    break;
                case 3:
                    System.out.print("Enter the title of the EBook to return: ");
                    String returnTitle = scanner.nextLine();
                    library.returnEBook(returnTitle);
                    break;
                case 4:
                    System.out.print("Enter the title to search: ");
                    String searchTitle = scanner.nextLine();
                    List<EBook> foundByTitle = library.searchByTitle(searchTitle);
                    if (foundByTitle.isEmpty()) {
                        System.out.println("No EBooks found with that title.");
                    } else {
                        System.out.println("Found EBooks:");
                        for (EBook eBook : foundByTitle) {
                            System.out.println(eBook);
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter the author to search: ");
                    String searchAuthor = scanner.nextLine();
                    List<EBook> foundByAuthor = library.searchByAuthor(searchAuthor);
                    if (foundByAuthor.isEmpty()) {
                        System.out.println("No EBooks found by that author.");
                    } else {
                        System.out.println("Found EBooks:");
                        for (EBook eBook : foundByAuthor) {
                            System.out.println(eBook);
                        }
                    }
                    break;