```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VirtualLibrary {

    static List<Ebook> ebooks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        addEbook("The Lord of the Rings", "J.R.R. Tolkien");
        addEbook("Pride and Prejudice", "Jane Austen");
        addEbook("1984", "George Orwell");
        addEbook("To Kill a Mockingbird", "Harper Lee");

        while (true) {
            System.out.println("\nWelcome to the Virtual Library");
            System.out.println("1. View available ebooks");
            System.out.println("2. Lend an ebook");
            System.out.println("3. Search for an ebook");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    viewEbooks();
                    break;
                case 2:
                    lendEbook();
                    break;
                case 3:
                    searchEbook();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void addEbook(String title, String author) {
        Ebook ebook = new Ebook(title, author);
        ebooks.add(ebook);
        System.out.println("Ebook added successfully.");
    }

    static void viewEbooks() {
        if (ebooks.isEmpty()) {
            System.out.println("No ebooks available.");
            return;
        }
        System.out.println("\nAvailable ebooks:");
        for (Ebook ebook : ebooks) {
            System.out.println(ebook);
        }
    }

    static void lendEbook() {
        System.out.println("\nLending an ebook:");
        System.out.print("Enter the title of the ebook to lend: ");
        String title = scanner.nextLine();
        for (Ebook ebook : ebooks) {
            if (ebook.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Lending " + ebook.getTitle());
                ebooks.remove(ebook);
                break;
            }
        }
    }

    static void searchEbook() {
        System.out.print("Enter the title of the ebook to search: ");
        String title = scanner.nextLine();
        boolean found = false;
        for (Ebook ebook : ebooks) {
            if (ebook.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Ebook found: " + ebook);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Ebook not found.");
        }
    }
}

class Ebook {
    private String title;
    private String author;

    public Ebook(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author;
    }
}
```