```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TriviaGame {
    private List<Player> players;
    private List<Round> rounds;
    private Random random;

    public TriviaGame() {
        this.players = new ArrayList<>();
        this.rounds = new ArrayList<>();
        this.random = new Random();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void addRound(Round round) {
        rounds.add(round);
    }

    public void startGame() {
        for (Round round : rounds) {
            System.out.println("Starting Round: " + round.getRoundNumber());
            for (Question question : round.getQuestions()) {
                askQuestion(question);
            }
        }
        announceWinner();
    }

    private void askQuestion(Question question) {
        System.out.println(question.getText());
        for (String option : question.getOptions()) {
            System.out.println(option);
        }
        int correctAnswerIndex = question.getCorrectAnswerIndex();
        for (Player player : players) {
            int playerAnswer = player.answerQuestion();
            if (playerAnswer == correctAnswerIndex) {
                player.incrementScore();
                System.out.println(player.getName() + " answered correctly!");
            } else {
                System.out.println(player.getName() + " answered incorrectly.");
            }
        }
    }

    private void announceWinner() {
        Player winner = players.stream().max((p1, p2) -> Integer.compare(p1.getScore(), p2.getScore())).orElse(null);
        if (winner != null) {
            System.out.println("The winner is: " + winner.getName() + " with a score of: " + winner.getScore());
        } else {
            System.out.println("No winner could be determined.");
        }
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

    public void incrementScore() {
        score++;
    }

    public int answerQuestion() {
        // Simulate player answering randomly
        return new Random().nextInt(4);
    }
}

class Round {
    private int roundNumber;
    private List<Question> questions;

    public Round(int roundNumber) {
        this.roundNumber = roundNumber;
        this.questions = new ArrayList<>();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

class Question {
    private String text;
    private List<String> options;
    private int correctAnswerIndex;

    public Question(String text, List<String> options, int correctAnswerIndex) {
        this.text = text;
        this.options = options;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }
}
```