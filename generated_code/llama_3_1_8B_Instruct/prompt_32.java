```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Question {
    private String question;
    private String type;
    private List<String> options;
    private String answer;
    private int points;

    public Question(String question, String type, List<String> options, String answer, int points) {
        this.question = question;
        this.type = type;
        this.options = options;
        this.answer = answer;
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public String getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }
}

public class QuizSystem {
    private List<Question> questions;
    private int score;
    private Scanner scanner;

    public QuizSystem() {
        this.questions = new ArrayList<>();
        this.score = 0;
        this.scanner = new Scanner(System.in);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void startQuiz() {
        for (Question question : questions) {
            System.out.println("Question: " + question.getQuestion());
            System.out.println("Type: " + question.getType());

            if (question.getType().equals("Multiple Choice")) {
                System.out.println("Options:");
                for (String option : question.getOptions()) {
                    System.out.println(option);
                }
                System.out.print("Enter the number of your answer: ");
                int choice = scanner.nextInt();
                if (question.getOptions().get(choice - 1).equals(question.getAnswer())) {
                    score += question.getPoints();
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct answer is: " + question.getAnswer());
                }
            } else if (question.getType().equals("True or False")) {
                System.out.print("Enter your answer (True/False): ");
                String answer = scanner.next();
                if (answer.equals(question.getAnswer())) {
                    score += question.getPoints();
                    System.out.println("Correct!");
                } else {
                    System.out.println("Incorrect. The correct answer is: " + question.getAnswer());
                }
            }
            System.out.println();
        }
        System.out.println("Quiz finished. Your score is: " + score);
    }

    public static void main(String[] args) {
        QuizSystem quizSystem = new QuizSystem();

        Question question1 = new Question("What is the capital of France?", "Multiple Choice",
                Arrays.asList("Paris", "London", "Berlin", "Madrid"),
                "Paris", 5);
        Question question2 = new Question("Is the sky blue?", "True or False", new ArrayList<>(),
                "True", 3);
        Question question3 = new Question("What is the largest planet in our solar system?", "Multiple Choice",
                Arrays.asList("Earth", "Saturn", "Jupiter", "Uranus"),
                "Jupiter", 4);

        quizSystem.addQuestion(question1);
        quizSystem.addQuestion(question2);
        quizSystem.addQuestion(question3);

        quizSystem.startQuiz();
    }
}
```