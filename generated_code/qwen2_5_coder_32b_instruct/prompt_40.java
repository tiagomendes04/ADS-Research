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

    public void displayPoll() {
        System.out.println("Poll: " + title);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getPrompt());
            questions.get(i).displayOptions();
        }
    }

    public void submitResponses(Map<Integer, List<String>> responses) {
        for (Map.Entry<Integer, List<String>> entry : responses.entrySet()) {
            int questionIndex = entry.getKey() - 1;
            if (questionIndex >= 0 && questionIndex < questions.size()) {
                questions.get(questionIndex).recordResponse(entry.getValue());
            }
        }
    }

    public void displayResults() {
        System.out.println("Poll Results for: " + title);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ". " + questions.get(i).getPrompt());
            questions.get(i).displayResults();
        }
    }
}

abstract class Question {
    protected String prompt;
    protected Map<String, Integer> optionCounts;

    public Question(String prompt) {
        this.prompt = prompt;
        this.optionCounts = new HashMap<>();
    }

    public String getPrompt() {
        return prompt;
    }

    abstract void displayOptions();

    abstract void recordResponse(List<String> response);

    abstract void displayResults();
}

class MultipleChoiceQuestion extends Question {
    private List<String> options;

    public MultipleChoiceQuestion(String prompt, List<String> options) {
        super(prompt);
        this.options = options;
        for (String option : options) {
            optionCounts.put(option, 0);
        }
    }

    @Override
    void displayOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("   " + (i + 1) + ": " + options.get(i));
        }
    }

    @Override
    void recordResponse(List<String> response) {
        for (String answer : response) {
            int index = Integer.parseInt(answer) - 1;
            if (index >= 0 && index < options.size()) {
                String option = options.get(index);
                optionCounts.put(option, optionCounts.get(option) + 1);
            }
        }
    }

    @Override
    void displayResults() {
        for (String option : options) {
            System.out.println("   " + option + ": " + optionCounts.get(option) + " votes");
        }
    }
}

class RankingQuestion extends Question {
    private List<String> options;

    public RankingQuestion(String prompt, List<String> options) {
        super(prompt);
        this.options = options;
        for (String option : options) {
            optionCounts.put(option, 0);
        }
    }

    @Override
    void displayOptions() {
        for (int i = 0; i < options.size(); i++) {
            System.out.println("   " + (i + 1) + ": " + options.get(i));
        }
    }

    @Override
    void recordResponse(List<String> response) {
        for (int i = 0; i < response.size(); i++) {
            int index = Integer.parseInt(response.get(i)) - 1;
            if (index >= 0 && index < options.size()) {
                String option = options.get(index);
                optionCounts.put(option, optionCounts.get(option) + (options.size() - i));
            }
        }
    }

    @Override
    void displayResults() {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(optionCounts.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        for (Map.Entry<String, Integer> entry : sortedList) {
            System.out.println("   " + entry.getKey() + ": " + entry.getValue() + " points");
        }
    }
}

public class PollingSystem {
    public static void main(String[] args) {
        Poll poll = new Poll("Sample Poll");

        List<String> mcOptions = Arrays.asList("Option A", "Option B", "Option C");
        MultipleChoiceQuestion mcQuestion = new MultipleChoiceQuestion("Choose your favorite option:", mcOptions);
        poll.addQuestion(mcQuestion);

        List<String> rOptions = Arrays.asList("Item 1", "Item 2", "Item 3");
        RankingQuestion rQuestion = new RankingQuestion("Rank these items from most to least favorite:", rOptions);
        poll.addQuestion(rQuestion);

        poll.displayPoll();

        Map<Integer, List<String>> responses = new HashMap<>();
        responses.put(1, Arrays.asList("1"));
        responses.put(2, Arrays.asList("1", "3", "2"));

        poll.submitResponses(responses);

        poll.displayResults();
    }
}
```