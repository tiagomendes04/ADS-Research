```java
import java.util.ArrayList;
import java.util.List;

public class TriviaGame {
    private List<Round> rounds;
    private List<Player> players;

    public TriviaGame() {
        rounds = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    public void startGame() {
        for (Round round : rounds) {
            round.play(players);
        }
    }

    public int getFinalScore() {
        int totalScore = 0;
        for (Player player : players) {
            totalScore += player.getScore();
        }
        return totalScore;
    }
}

class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore(int points) {
        score += points;
    }
}

class Round {
    private List<Question> questions;

    public Round(List<Question> questions) {
        this.questions = questions;
    }

    public void play(List<Player> players) {
        for (Question question : questions) {
            question.ask(players);
        }
    }
}

class Question {
    private String text;
    private int points;
    private String correctAnswer;

    public Question(String text, int points, String correctAnswer) {
        this.text = text;
        this.points = points;
        this.correctAnswer = correctAnswer;
    }

    public void ask(List<Player> players) {
        for (Player player : players) {
            String answer = player.answerQuestion(text);
            if (answer.equalsIgnoreCase(correctAnswer)) {
                player.incrementScore(points);
                System.out.println(player.getName() + " answered correctly and gained " + points + " points!");
            } else {
                System.out.println(player.getName() + " answered incorrectly.");
            }
        }
    }
}

class PlayerAnswer {
    private String name;
    private String answer;

    public PlayerAnswer(String name, String answer) {
        this.name = name;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public String getAnswer() {
        return answer;
    }
}

class GameDriver {
    public static void main(String[] args) {
        TriviaGame game = new TriviaGame();
        game.addPlayer(new Player("Alice"));
        game.addPlayer(new Player("Bob"));

        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", 10, "Paris"));
        questions.add(new Question("What is 2 + 2?", 5, "4"));

        game.addRound(new Round(questions));
        game.startGame();

        int finalScore = game.getFinalScore();
        System.out.println("Final score: " + finalScore);
    }
}
```