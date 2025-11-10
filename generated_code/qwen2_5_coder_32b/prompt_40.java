```java
import java.util.*;

class Poll {
    private String title;
    private List<Question> questions;

    public Poll(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void conductPoll() {
        System.out.println("Poll: " + title);
        for (Question question : questions) {
            question.display();
            question.collectResponse();
        }
    }

    public void displayResults() {
        for (Question question : questions) {
            question.displayResults();
        }
    }
}

abstract class Question {
    protected String prompt;

    public Question(String prompt) {
        this.prompt = prompt;
    }

    public abstract void display();

    public abstract void collectResponse();

    public abstract void displayResults();
}

class MultipleChoiceQuestion extends Question {
    private List<String> options;
    private Map<String, Integer> responseCount;

    public MultipleChoiceQuestion(String prompt, List<String> options) {
        super(prompt);
        this.options = options;
        this.responseCount = new HashMap<>();
        for (String option : options) {
            responseCount.put(option, 0);
        }
    }

    @Override
    public void display() {
        System.out.println(prompt);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Enter your choice: ");
    }

    @Override
    public void collectResponse() {
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= options.size()) {
            String selectedOption = options.get(choice - 1);
            responseCount.put(selectedOption, responseCount.getOrDefault(selectedOption, 0) + 1);
        } else {
            System.out.println("Invalid choice. Please try again.");
            collectResponse();
        }
    }

    @Override
    public void displayResults() {
        System.out.println("Results for: " + prompt);
        for (Map.Entry<String, Integer> entry : responseCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
    }
}

class RankingQuestion extends Question {
    private List<String> items;
    private Map<String, Integer> rankCount;

    public RankingQuestion(String prompt, List<String> items) {
        super(prompt);
        this.items = items;
        this.rankCount = new HashMap<>();
        for (String item : items) {
            rankCount.put(item, 0);
        }
    }

    @Override
    public void display() {
        System.out.println(prompt);
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i));
        }
        System.out.println("Please rank the items by entering their numbers separated by spaces:");
    }

    @Override
    public void collectResponse() {
        Scanner scanner = new Scanner(System.in);
        String[] ranks = scanner.nextLine().split(" ");
        if (ranks.length != items.size()) {
            System.out.println("Invalid input. Please rank all items.");
            collectResponse();
        } else {
            try {
                int[] rankNumbers = Arrays.stream(ranks).mapToInt(Integer::parseInt).toArray();
                Arrays.sort(rankNumbers);
                for (int i = 0; i < rankNumbers.length; i++) {
                    if (rankNumbers[i] < 1 || rankNumbers[i] > items.size()) {
                        throw new NumberFormatException();
                    }
                    String rankedItem = items.get(rankNumbers[i] - 1);
                    rankCount.put(rankedItem, rankCount.getOrDefault(rankedItem, 0) + (items.size() - i));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numbers only.");
                collectResponse();
            }
        }
    }

    @Override
    public void displayResults() {
        System.out.println("Results for: " + prompt);
        rankCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " points"));
    }
}

public class PollingSystem {
    public static void main(String[] args) {
        Poll poll = new Poll("Sample Poll");

        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion("What is your favorite color?",
                Arrays.asList("Red", "Blue", "Green", "Yellow"));
        poll.addQuestion(mcq);

        RankingQuestion rq = new RankingQuestion("Rank these fruits from most to least favorite:",
                Arrays.asList("Apple", "Banana", "Cherry", "Date"));
        poll.addQuestion(rq);

        poll.conductPoll();