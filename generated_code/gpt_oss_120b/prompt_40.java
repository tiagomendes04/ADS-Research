```java
import java.util.*;

abstract class Question {
    protected String prompt;
    public Question(String prompt) { this.prompt = prompt; }
    public String getPrompt() { return prompt; }
    public abstract void display();
    public abstract void collectAnswer(Scanner sc);
    public abstract void showResult();
}

class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private Map<Integer, Integer> votes = new HashMap<>();
    private int selectedOption = -1;

    public MultipleChoiceQuestion(String prompt, List<String> options) {
        super(prompt);
        this.options = options;
        for (int i = 0; i < options.size(); i++) votes.put(i, 0);
    }

    @Override
    public void display() {
        System.out.println(prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, options.get(i));
        }
    }

    @Override
    public void collectAnswer(Scanner sc) {
        System.out.print("Select an option (1-" + options.size() + "): ");
        int choice = sc.nextInt();
        sc.nextLine(); // consume newline
        if (choice < 1 || choice > options.size()) {
            System.out.println("Invalid choice. Skipping.");
            return;
        }
        selectedOption = choice - 1;
        votes.put(selectedOption, votes.get(selectedOption) + 1);
    }

    @Override
    public void showResult() {
        System.out.println("Results for: " + prompt);
        int total = votes.values().stream().mapToInt(Integer::intValue).sum();
        for (int i = 0; i < options.size(); i++) {
            int count = votes.get(i);
            double percent = total == 0 ? 0 : (count * 100.0 / total);
            System.out.printf("%s : %d votes (%.2f%%)%n", options.get(i), count, percent);
        }
        System.out.println();
    }
}

class RankingQuestion extends Question {
    private List<String> items;
    private List<Integer> rankings = new ArrayList<>();

    public RankingQuestion(String prompt, List<String> items) {
        super(prompt);
        this.items = items;
    }

    @Override
    public void display() {
        System.out.println(prompt);
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, items.get(i));
        }
        System.out.println("Rank the items from 1 (best) to " + items.size() + " (worst).");
    }

    @Override
    public void collectAnswer(Scanner sc) {
        rankings.clear();
        Set<Integer> used = new HashSet<>();
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("Rank for \"%s\": ", items.get(i));
            int rank = sc.nextInt();
            sc.nextLine(); // consume newline
            if (rank < 1 || rank > items.size() || used.contains(rank)) {
                System.out.println("Invalid or duplicate rank. Skipping this question.");
                rankings.clear();
                return;
            }
            rankings.add(rank);
            used.add(rank);
        }
    }

    @Override
    public void showResult() {
        if (rankings.isEmpty()) {
            System.out.println("No rankings submitted for: " + prompt);
            return;
        }
        System.out.println("Aggregated Rankings for: " + prompt);
        int n = items.size();
        int[] totalRanks = new int[n];
        for (int i = 0; i < n; i++) totalRanks[i] = rankings.get(i);
        // Simple average ranking (since only one response per run)
        List<Integer> order = new ArrayList<>();
        for (int i = 0; i < n; i++) order.add(i);
        order.sort(Comparator.comparingInt(i -> totalRanks[i]));
        for (int idx : order) {
            System.out.printf("%s : Rank %d%n", items.get(idx), totalRanks[idx]);
        }
        System.out.println();
    }
}

class Poll {
    private String title;
    private List<Question> questions = new ArrayList<>();

    public Poll(String title) { this.title = title; }

    public void addQuestion(Question q) { questions.add(q); }

    public void conduct(Scanner sc) {
        System.out.println("=== Poll: " + title + " ===");
        for (Question q : questions) {
            q.display();
            q.collectAnswer(sc);
            System.out.println();
        }
    }

    public void showResults() {
        System.out.println("=== Results for Poll: " + title + " ===");
        for (Question q : questions) {
            q.showResult();
        }
    }
}

public class OnlinePollingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Poll> polls = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1) Create Poll");
            System.out.println("2) Take Poll");
            System.out.println("3) View Results");
            System.out.println("4) Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1 -> createPoll();
                case 2 -> takePoll();
                case 3 -> viewResults();
                case 4 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void createPoll() {
        System.out.print("Enter poll title: ");
        String title = scanner.nextLine();
        Poll poll = new Poll(title);
        while (true) {
            System.out.println("Add Question:");
            System.out.println("1) Multiple Choice");
            System.out.println("2) Ranking");
            System.out.println("3) Done");
            System.out.print("Choose: ");
            int type = scanner.nextInt();
            scanner.nextLine();
            if (type == 3) break;
            System.out.print("Enter question prompt: ");
            String prompt = scanner.nextLine();
            if (type == 1) {
                List<String> opts = new ArrayList<>();
                System.out.println("Enter options (blank line to finish):");
                while (true) {
                    String opt = scanner.nextLine();
                    if (opt.isBlank()) break;
                    opts.add(opt);
                }
                poll.addQuestion(new MultipleChoiceQuestion(prompt, opts));
            } else if (type == 2) {
                List<String> items = new ArrayList<>();
                System.out.println("Enter items to rank (blank line to finish):");
                while (true) {
                    String itm = scanner.nextLine();
                    if (itm.isBlank()) break;
                    items.add(itm);
                }
                poll.addQuestion(new RankingQuestion(prompt, items));
            }
        }
        polls.add(poll);
        System.out.println("Poll created.\n");
    }

    private static void takePoll() {
        if (polls.isEmpty()) {
            System.out.println("No polls available.\n");
            return;
        }
        Poll poll = selectPoll();
        if (poll != null) poll.conduct(scanner);
    }

    private static void viewResults() {
        if (polls.isEmpty()) {
            System.out.println("No polls available.\n");
            return;
        }
        Poll poll = selectPoll();
        if (poll != null) poll.showResults();
    }

    private static Poll selectPoll() {
        System.out.println("Available Polls:");
        for (int i = 0; i < polls.size(); i++) {
            System.out.printf("%d) %s%n", i + 1, polls.get(i).title);
        }
        System.out.print("Select poll number: ");
        int idx = scanner.nextInt();
        scanner.nextLine();
        if (idx < 1 || idx > polls.size()) {
            System.out.println("Invalid selection.\n");
            return null;
        }
        return polls.get(idx - 1);
    }
}
```