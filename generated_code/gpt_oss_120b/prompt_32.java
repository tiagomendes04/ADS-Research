```java
import java.util.*;

public class QuizApp {
    public static void main(String[] args) {
        List<Question> quiz = new ArrayList<>();
        quiz.add(new MultipleChoiceQuestion(
                "What is the capital of France?",
                Arrays.asList("Berlin", "London", "Paris", "Rome"),
                2));
        quiz.add(new TrueFalseQuestion(
                "The Java programming language was released in 1995.",
                true));
        quiz.add(new ShortAnswerQuestion(
                "Name the largest planet in our Solar System.",
                new HashSet<>(Arrays.asList("jupiter"))));

        Scanner scanner = new Scanner(System.in);
        int score = 0;
        System.out.println("=== Quiz Started ===\n");

        for (int i = 0; i < quiz.size(); i++) {
            Question q = quiz.get(i);
            System.out.println("Question " + (i + 1) + ":");
            q.ask();
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine().trim();
            if (q.checkAnswer(userAnswer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Incorrect. " + q.getCorrectAnswerInfo() + "\n");
            }
        }

        System.out.println("=== Quiz Finished ===");
        System.out.println("Your score: " + score + " out of " + quiz.size());
        scanner.close();
    }
}

/* ==================== Question Hierarchy ==================== */

abstract class Question {
    protected String prompt;

    public Question(String prompt) {
        this.prompt = prompt;
    }

    public void ask() {
        System.out.println(prompt);
        displayOptions();
    }

    protected void displayOptions() {
        // Default: no options
    }

    public abstract boolean checkAnswer(String answer);

    public abstract String getCorrectAnswerInfo();
}

/* -------------------- Multiple Choice -------------------- */

class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private int correctIndex; // zero‑based

    public MultipleChoiceQuestion(String prompt, List<String> options, int correctIndex) {
        super(prompt);
        this.options = new ArrayList<>(options);
        this.correctIndex = correctIndex;
    }

    @Override
    protected void displayOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        try {
            int choice = Integer.parseInt(answer);
            return choice - 1 == correctIndex;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public String getCorrectAnswerInfo() {
        return "Correct answer: " + (correctIndex + 1) + ". " + options.get(correctIndex);
    }
}

/* -------------------- True / False -------------------- */

class TrueFalseQuestion extends Question {
    private boolean correct;

    public TrueFalseQuestion(String prompt, boolean correct) {
        super(prompt + " (true/false)");
        this.correct = correct;
    }

    @Override
    public boolean checkAnswer(String answer) {
        String normalized = answer.trim().toLowerCase();
        if (normalized.equals("true") || normalized.equals("t")) {
            return correct;
        } else if (normalized.equals("false") || normalized.equals("f")) {
            return !correct;
        }
        return false;
    }

    @Override
    public String getCorrectAnswerInfo() {
        return "Correct answer: " + (correct ? "True" : "False");
    }
}

/* -------------------- Short Answer -------------------- */

class ShortAnswerQuestion extends Question {
    private Set<String> acceptableAnswers; // stored lower‑cased

    public ShortAnswerQuestion(String prompt, Set<String> acceptableAnswers) {
        super(prompt);
        this.acceptableAnswers = new HashSet<>();
        for (String ans : acceptableAnswers) {
            this.acceptableAnswers.add(ans.toLowerCase());
        }
    }

    @Override
    public boolean checkAnswer(String answer) {
        return acceptableAnswers.contains(answer.trim().toLowerCase());
    }

    @Override
    public String getCorrectAnswerInfo() {
        return "Acceptable answer(s): " + String.join(", ", acceptableAnswers);
    }
}
```