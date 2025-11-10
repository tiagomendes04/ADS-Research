```java
import java.util.Scanner;

class Question {
    String text;
    String[] options;
    int correctAnswer;

    public Question(String text, String[] options, int correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }
}

class MultipleChoiceQuestion extends Question {
    public MultipleChoiceQuestion(String text, String[] options, int correctAnswer) {
        super(text, options, correctAnswer);
    }
}

class TrueFalseQuestion extends Question {
    public TrueFalseQuestion(String text, int correctAnswer) {
        super(text, new String[]{"True", "False"}, correctAnswer);
    }

    @Override
    public boolean checkAnswer(int answer) {
        return answer == correctAnswer;
    }
}

public class QuizSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Question[] questions = {
            new MultipleChoiceQuestion("What is the capital of France?", new String[]{"Paris", "Berlin", "Madrid", "Rome"}, 0),
            new TrueFalseQuestion("Java is a programming language.", 0),
            new MultipleChoiceQuestion("Which of the following is a prime number? (1, 4, 6, 7)", new String[]{"1", "4", "6", "7"}, 3)
        };
        int score = 0;

        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i].text);
            for (int j = 0; j < questions[i].options.length; j++) {
                System.out.println((j + 1) + ". " + questions[i].options[j]);
            }
            System.out.print("Your answer: ");
            int answer = scanner.nextInt();
            if (questions[i] instanceof MultipleChoiceQuestion) {
                MultipleChoiceQuestion mcq = (MultipleChoiceQuestion) questions[i];
                if (mcq.checkAnswer(answer - 1)) {
                    score++;
                }
            } else if (questions[i] instanceof TrueFalseQuestion) {
                TrueFalseQuestion tfq = (TrueFalseQuestion) questions[i];
                if (tfq.checkAnswer(answer)) {
                    score++;
                }
            }
        }

        System.out.println("Your final score is: " + score + "/" + questions.length);
        scanner.close();
    }
}
```