```java
import java.util.Scanner;

public class QuizSystem {

    static Scanner scanner = new Scanner(System.in);
    static int score = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to the Quiz System!");
        while (true) {
            System.out.println("Choose a question type:");
            System.out.println("1. Multiple Choice");
            System.out.println("2. True/False");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    multipleChoiceQuestion();
                    break;
                case 2:
                    trueFalseQuestion();
                    break;
                case 3:
                    System.out.println("Exiting the Quiz System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void multipleChoiceQuestion() {
        System.out.println("Multiple Choice Question:");
        System.out.println("1. Answer A");
        System.out.println("2. Answer B");
        System.out.println("3. Answer C");
        System.out.println("4. Answer D");
        System.out.println("Enter your answer:");

        int answer = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (answer == 1) {
            score++;
            System.out.println("Correct! +1 point.");
        } else {
            System.out.println("Incorrect. The correct answer is A");
        }
    }

    static void trueFalseQuestion() {
        System.out.println("True/False Question:");
        System.out.println("1. Is it raining?");
        System.out.println("2. Is the sky blue?");
        System.out.println("Enter your answer:");

        String answer = scanner.nextLine();
        if (answer.equals("Yes")) {
            score++;
            System.out.println("Correct! +1 point.");
        } else {
            System.out.println("Incorrect. The correct answer is Yes");
        }
    }
}
```