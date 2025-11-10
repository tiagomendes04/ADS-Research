```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class OnlinePollingSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Question List
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("What is your favorite programming language?", "Java", "Python", "C++", "JavaScript", "Others"));
        questions.add(new Question("What is your favorite operating system?", "Windows", "MacOS", "Linux", "Others"));
        questions.add(new Question("What is your favorite food?", "Pizza", "Sushi", "Burger", "Pasta", "Others"));
        questions.add(new Question("Rank your favorite programming languages from 1 to 5.", "Java", "Python", "C++", "JavaScript", "Others"));

        // User data
        HashMap<String, Integer> userAnswers = new HashMap<>();

        // Start polling
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            for (String option : question.getOptions()) {
                System.out.println("- " + option);
            }

            // Get user answer
            System.out.print("Enter your answer (1-5) or 'exit' to finish: ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("exit")) {
                break;
            }

            // Process answer
            if (answer.matches("\\d+")) {
                userAnswers.put(question.getQuestion(), Integer.parseInt(answer));
            }
        }
        scanner.close();
    }

}

class Question {
    private String question;
    private String[] options;

    public Question(String question, String... options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }
}
```