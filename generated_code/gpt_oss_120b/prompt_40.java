```java
import java.util.*;

abstract class Question {
    protected final String id;
    protected final String text;

    public Question(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getId() { return id; }
    public String getText() { return text; }

    public abstract void display();
    public abstract void collectAnswer(Scanner scanner);
    public abstract void showResults();
}

class MultipleChoiceQuestion extends Question {
    private final List<String> options;
    private final Map<Integer, Integer> voteCount = new HashMap<>();
    private int selectedOption = -1;

    public MultipleChoiceQuestion(String id, String text, List<String> options) {
        super(id, text);
        this.options = new ArrayList<>(options);
        for (int i = 0; i < options.size(); i++) voteCount.put(i, 0);
    }

    @Override
    public void display() {
        System.out.println("\n[" + id + "] " + text);
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, options.get(i));
        }
    }

    @Override
    public void collectAnswer(Scanner scanner) {
        System.out.print("Select an option (1-" + options.size() + "): ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        if (choice < 1 || choice > options.size()) {
            System.out.println("Invalid choice, ignored.");
            return;
        }
        selectedOption = choice - 1;
        voteCount.put(selectedOption, voteCount.get(selectedOption) + 1);
    }

    @Override
    public void showResults() {
        System.out.println("\nResults for [" + id + "] " + text);
        int total = voteCount.values().stream().mapToInt(Integer::intValue).sum();
        for (int i = 0; i < options.size(); i++) {
            int cnt = voteCount.get(i);
            double perc = total == 0 ? 0 : (cnt * 100.0 / total);
            System.out.printf("%d) %s – %d votes (%.2f%%)%n", i + 1, options.get(i), cnt, perc);
        }
    }
}

class RankingQuestion extends Question {
    private final List<String> items;
    private final List<List<Integer>> rankings = new ArrayList<>();

    public RankingQuestion(String id, String text, List<String> items) {
        super(id, text);
        this.items = new ArrayList<>(items);
    }

    @Override
    public void display() {
        System.out.println("\n[" + id + "] " + text);
        System.out.println("Rank the following items (enter numbers separated by spaces, highest rank first):");
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d) %s%n", i + 1, items.get(i));
        }
    }

    @Override
    public void collectAnswer(Scanner scanner) {
        System.out.print("Your ranking: ");
        String line = scanner.nextLine().trim();
        String[] parts = line.split("\\s+");
        if (parts.length != items.size()) {
            System.out.println("Incorrect number of items, ignored.");
            return;
        }
        List<Integer> rank = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (String p : parts) {
            try {
                int idx = Integer.parseInt(p) - 1;
                if (idx < 0 || idx >= items.size() || seen.contains(idx)) {
                    System.out.println("Invalid ranking, ignored.");
                    return;
                }
                rank.add(idx);
                seen.add(idx);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, ignored.");
                return;
            }
        }
        rankings.add(rank);
    }

    @Override
    public void showResults() {
        System.out.println("\nResults for [" + id + "] " + text);
        int n = items.size();
        double[] scores = new double[n];
        for (List<Integer> rank : rankings) {
            for (int pos = 0; pos < n; pos++) {
                int idx = rank.get(pos);
                scores[idx] += (n - pos); // higher rank gets more points
            }
        }
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) order.add(i);
        order.sort((a, b) -> Double.compare(scores[b], scores[a]));

        System.out.println("Average scores:");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d) %s – %.2f%n", i + 1, items.get(i), scores[i] / Math.max(1, rankings.size()));
        }

        System.out.println("\nOverall ranking:");
        for (int i = 0; i < n; i++) {
            int idx = order.get(i);
            System.out.printf("%d) %s (score: %.2f)%n", i + 1, items.get(idx), scores[idx] / Math.max(1, rankings.size()));
        }
    }
}

class Poll {
    private final String title;
    private final List<Question> questions = new ArrayList<>();

    public Poll(String title) {
        this.title = title;
    }

    public void addQuestion(Question q) { questions.add(q); }

    public void conduct(Scanner scanner) {
        System.out.println("\n=== " + title + " ===");
        for (Question q : questions) {
            q.display();
            q.collectAnswer(scanner);
        }
    }

    public void showResults() {
        System.out.println("\n=== Results for " + title + " ===");
        for (Question q : questions) {
            q.showResults();
        }
    }
}

public class OnlinePollingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Poll> polls = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- Online Polling System ---");
            System.out.println("1) Create new poll");
            System.out.println("2) Take a poll");
            System.out.println("3) Show poll results");
            System.out.println("4) Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> createPoll();
                case "2" -> takePoll();
                case "3" -> showResults();
                case "4" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void createPoll() {
        System.out.print("Enter poll title: ");
        String title = scanner.nextLine().trim();
        Poll poll = new Poll(title);
        while (true) {
            System.out.println("\nAdd question:");
            System.out.println("a) Multiple-choice");
            System.out.println("b) Ranking");
            System.out.println("c) Done");
            System.out.print("Select type: ");
            String type = scanner.nextLine().trim().toLowerCase();
            if (type.equals("c")) break;
            System.out.print("Question ID: ");
            String id = scanner.nextLine().trim();
            System.out.print("Question text: ");
            String text = scanner.nextLine().trim();
            switch (type) {
                case "a" -> {
                    List<String> opts = readOptions();
                    poll.addQuestion(new MultipleChoiceQuestion(id, text, opts));
                }
                case "b" -> {
                    List<String> items = readOptions();
                    poll.addQuestion(new RankingQuestion(id, text, items));
                }
                default -> System.out.println("Unknown type.");
            }
        }
        polls.add(poll);
        System.out.println("Poll created.");
    }

    private static List<String> readOptions() {
        List<String> opts = new ArrayList<>();
        System.out.println("Enter options/items (blank line to finish):");
        while (true) {
            System.out.print("> ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) break;
            opts.add(line);
        }
        return opts;
    }

    private static void takePoll() {
        if (polls.isEmpty()) {
            System.out.println("No polls available.");
            return;
        }
        Poll poll = selectPoll();
        if (poll != null) poll.conduct(scanner);
    }

    private static void showResults() {
        if (polls.isEmpty()) {
            System.out.println("No polls available.");
            return;
        }
        Poll poll = selectPoll();
        if (poll != null) poll.showResults();
    }

    private static Poll selectPoll() {
        System.out.println("\nAvailable polls:");
        for (int i = 0; i < polls.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, polls.get(i).title);
        }