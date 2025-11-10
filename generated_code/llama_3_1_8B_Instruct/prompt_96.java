```java
// Question.java
public class Question {
    private String text;
    private String answer;
    private int points;

    public Question(String text, String answer, int points) {
        this.text = text;
        this.answer = answer;
        this.points = points;
    }

    public String getText() {
        return text;
    }

    public String getAnswer() {
        return answer;
    }

    public int getPoints() {
        return points;
    }
}

// Player.java
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int score;
    private List<Question> answeredQuestions;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.answeredQuestions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public void addAnsweredQuestion(Question question) {
        this.answeredQuestions.add(question);
    }

    public List<Question> getAnsweredQuestions() {
        return answeredQuestions;
    }
}

// QuestionBank.java
import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private List<Question> questions;

    public QuestionBank() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

// Round.java
public class Round {
    private Question question;
    private int timeLimit;

    public Round(Question question, int timeLimit) {
        this.question = question;
        this.timeLimit = timeLimit;
    }

    public Question getQuestion() {
        return question;
    }

    public int getTimeLimit() {
        return timeLimit;
    }
}

// Game.java
public class Game {
    private List<Player> players;
    private QuestionBank questionBank;
    private List<Round> rounds;

    public Game(List<Player> players, QuestionBank questionBank) {
        this.players = players;
        this.questionBank = questionBank;
        this.rounds = new ArrayList<>();
    }

    public void addRound(Round round) {
        this.rounds.add(round);
    }

    public void playRound(Round round) {
        Question question = round.getQuestion();
        for (Player player : players) {
            System.out.println(player.getName() + ", you have " + round.getTimeLimit() + " seconds to answer this question:");
            System.out.println(question.getText());
            String answer = player.getName() + "'s answer";
            if (question.getAnswer().equals(answer)) {
                player.addScore(question.getPoints());
                System.out.println(player.getName() + " answered correctly and earned " + question.getPoints() + " points!");
            } else {
                System.out.println(player.getName() + " answered incorrectly.");
            }
            player.addAnsweredQuestion(question);
        }
    }

    public void startGame() {
        for (Round round : rounds) {
            playRound(round);
        }
        for (Player player : players) {
            System.out.println(player.getName() + " has a total score of " + player.getScore() + " points.");
        }
    }
}
```

```java
// Main.java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        QuestionBank questionBank = new QuestionBank();
        Question question1 = new Question("What is the capital of France?", "Paris", 10);
        Question question2 = new Question("What is the largest planet in our solar system?", "Jupiter", 20);
        questionBank.addQuestion(question1);
        questionBank.addQuestion(question2);

        List<Player> players = new ArrayList<>();
        players.add(new Player("John"));
        players.add(new Player("Alice"));

        Game game = new Game(players, questionBank);
        Round round1 = new Round(question1, 30);
        Round round2 = new Round(question2, 45);
        game.addRound(round1);
        game.addRound(round2);

        game.startGame();
    }
}
```