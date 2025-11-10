```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Question {
    String text;
    int score;

    public Question(String text, int score) {
        this.text = text;
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

class TrueFalseQuestion extends Question {
    boolean answer;

    public TrueFalseQuestion(String text, boolean answer, int score) {
        super(text, score);
        this.answer = answer;
    }

    public boolean isCorrect(boolean userAnswer) {
        return this.answer == userAnswer;
    }
}

class MultipleChoiceQuestion extends Question {
    List<String> options;
    int correctOptionIndex;

    public MultipleChoiceQuestion(String text, List<String> options, int correctOptionIndex, int score) {
        super(text, score);
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public boolean isCorrect(int userAnswerIndex) {
        return this.correctOptionIndex == userAnswerIndex;
    }
}

class Quiz {
    List<Question> questions;

    public Quiz() {
        questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int runQuiz() {
        Scanner scanner = new Scanner(System.in);
        int totalScore = 0;

        for (Question question : questions) {
            System.out.println(question.text);

            if (question instanceof TrueFalseQuestion) {
                System.out.println("Enter 'true' or 'false':");
                boolean userAnswer = Boolean.parseBoolean(scanner.nextLine());
                if (((TrueFalseQuestion) question).isCorrect(userAnswer)) {
                    totalScore += question.getScore();
                }
            } else if (question instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcQuestion = (MultipleChoiceQuestion) question;
                for (int i = 0; i < mcQuestion.options.size(); i++) {
                    System.out.println((i + 1) + ": " + mcQuestion.options.get(i));
                }
                System.out.println("Enter the number of your answer:");
                int userAnswerIndex = Integer.parseInt(scanner.nextLine()) - 1;
                if (mcQuestion.isCorrect(userAnswerIndex)) {
                    totalScore += question.getScore();
                }
            }
        }

        scanner.close();
        return totalScore;
    }
}

public class QuizSystem {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();

        TrueFalseQuestion q1 = new TrueFalseQuestion("The sky is blue.", true, 5);
        quiz.addQuestion(q1);

        List<String> optionsQ2 = new ArrayList<>();
        optionsQ2.add("Red");
        optionsQ2.add("Blue");
        optionsQ2.add("Green");
        optionsQ2.add("Yellow");
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion("What color is the sky on a clear day?", optionsQ2, 1, 10);
        quiz.addQuestion(q2);

        TrueFalseQuestion q3 = new TrueFalseQuestion("Water boils at 100 degrees Celsius.", true, 5);
        quiz.addQuestion(q3);

        int totalScore = quiz.runQuiz();
        System.out.println("Your total score is: " + totalScore);
    }
}
```