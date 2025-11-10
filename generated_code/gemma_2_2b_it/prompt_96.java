```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TriviaGame {

    private List<Round> rounds;
    private List<Player> players;

    public TriviaGame() {
        rounds = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void startGame() {
        Random random = new Random();
        int currentRound = 0;
        for (Round round : rounds) {
            System.out.println("Round " + (currentRound + 1) + ":");
            for (Question question : round.getQuestions()) {
                System.out.println(question.getQuestionText());
                String answer = getAnswerFromPlayer(question);
                if (answer.equals(question.getAnswer())) {
                    System.out.println("Correct!");
                    System.out.println(question.getAnswer());
                    System.out.println("Score: " + getScore());
                } else {
                    System.out.println("Incorrect!");
                }
            }
            currentRound++;
        }
    }

    private String getAnswerFromPlayer(Question question) {
        // Implement your logic here
        return "Answer";
    }

    private int getScore() {
        // Implement your logic here
        return 0;
    }
}

class Round {
    private List<Question> questions;

    public Round(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Question {
    private String questionText;
    private String answer;

    public Question(String questionText, String answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getAnswer() {
        return answer;
    }
}

class Player {
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```