```java
import java.util.*;

public class TriviaGame {
    private final List<Player> players = new ArrayList<>();
    private final List<Round> rounds = new ArrayList<>();
    private int currentRound = 0;

    public TriviaGame(List<Player> players, List<Round> rounds) {
        this.players.addAll(players);
        this.rounds.addAll(rounds);
    }

    public void start() {
        while (currentRound < rounds.size()) {
            Round round = rounds.get(currentRound);
            round.play(players);
            currentRound++;
        }
        displayFinalScores();
    }

    private void displayFinalScores() {
        players.sort(Comparator.comparingInt(Player::getScore).reversed());
        System.out.println("=== Final Scores ===");
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getScore());
        }
    }

    public static void main(String[] args) {
        List<Player> players = Arrays.asList(
                new Player("Alice"),
                new Player("Bob"),
                new Player("Charlie")
        );

        List<Round> rounds = Arrays.asList(
                new Round(Arrays.asList(
                        new Question("Capital of France?", new String[]{"Paris", "Berlin", "Rome", "Madrid"}, 0),
                        new Question("5 + 7 = ?", new String[]{"10", "11", "12", "13"}, 2)
                )),
                new Round(Arrays.asList(
                        new Question("Largest planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 2),
                        new Question("Water's chemical formula?", new String[]{"H2O", "CO2", "O2", "NaCl"}, 0)
                ))
        );

        new TriviaGame(players, rounds).start();
    }
}

class Player {
    private final String name;
    private int score = 0;

    public Player(String name) {
        this.name = name;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

class Round {
    private final List<Question> questions;

    public Round(List<Question> questions) {
        this.questions = new ArrayList<>(questions);
    }

    public void play(List<Player> players) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- New Round ---");
        for (Question q : questions) {
            System.out.println("\n" + q.getPrompt());
            String[] opts = q.getOptions();
            for (int i = 0; i < opts.length; i++) {
                System.out.println((i + 1) + ". " + opts[i]);
            }
            for (Player p : players) {
                System.out.print(p.getName() + ", enter your answer (1-" + opts.length + "): ");
                int answer = scanner.nextInt() - 1;
                if (answer == q.getCorrectIndex()) {
                    p.addScore(10);
                }
            }
        }
    }
}

class Question {
    private final String prompt;
    private final String[] options;
    private final int correctIndex;

    public Question(String prompt, String[] options, int correctIndex) {
        this.prompt = prompt;
        this.options = Arrays.copyOf(options, options.length);
        this.correctIndex = correctIndex;
    }

    public String getPrompt() {
        return prompt;
    }

    public String[] getOptions() {
        return Arrays.copyOf(options, options.length);
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
```