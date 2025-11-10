```java
import java.util.ArrayList;
import java.util.List;

public class TriviaGame {
    private int rounds;
    private List<Question> questions;
    private int score;
    private int currentRound;

    public TriviaGame() {
        this.rounds = 0;
        this.questions = new ArrayList<>();
        this.score = 0;
        this.currentRound = 0;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void start() {
        for (Question question : this.questions) {
            question.start();
        }
        this.currentRound = 0;
    }

    public void endRound() {
        this.currentRound++;
        if (this.currentRound < this.questions.size()) {
            this.questions.get(this.currentRound).endRound();
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getRoundNumber() {
        return this.currentRound;
    }

    public static class Question {
        private String question;
        private String[] answers;
        private String correctAnswer;

        public Question(String question, String[] answers, String correctAnswer) {
            this.question = question;
            this.answers = answers;
            this.correctAnswer = correctAnswer;
        }

        public void start() {
            System.out.println(question);
            for (String answer : answers) {
                System.out.println("A) " + answer + ", B) " + answer + ", C) " + answer);
            System.out.print("Enter your answer: ");
            String userAnswer = input();
            if (userAnswer.equalsIgnoreCase(correctAnswer)) {
                System.out.println("Correct!");
                this.score++;
            } else {
                System.out.println("Incorrect. The correct answer is " + correctAnswer);
            }
        }

        public void endRound() {
            System.out.println("Round " + this.currentRound + " complete.");
        }
    }

    public static class Input {
        public static String input() {
            // Simulating user input
            return "Enter your answer: ";
        }
    }
}
```

```java
import java.util.ArrayList;
import java.util.List;

public class GameRound {
    private TriviaGame game;
    private int index;

    public GameRound(TriviaGame game) {
        this.game = game;
        this.index = 0;
    }

    public void addQuestion(Question question) {
        question.addQuestion(this);
    }

    public void start() {
        System.out.println("Round " + this.index + " of " + game.getRoundNumber() + " has started.");
        question = game.getQuestions().get(this.index);
        question.start();
    }

    public int getScore() {
        return game.getScore();
    }

    public String getRoundName() {
        return game.getRoundNumber() + " Round";
    }

    public List<Question> getQuestions() {
        return game.getQuestions();
    }
}
```