```java
import java.util.*;

class Question {
    String id;
    String type;
    List<String> options;

    public Question(String id, String type, List<String> options) {
        this.id = id;
        this.type = type;
        this.options = options;
    }
}

class MultipleChoiceQuestion extends Question {
    String correctAnswer;

    public MultipleChoiceQuestion(String id, String type, List<String> options, String correctAnswer) {
        super(id, type, options);
        this.correctAnswer = correctAnswer;
    }
}

class RankingQuestion extends Question {
    int totalOptions;

    public RankingQuestion(String id, String type, List<String> options, int totalOptions) {
        super(id, type, options);
        this.totalOptions = totalOptions;
    }
}

class Poll {
    String id;
    List<Question> questions;
    Map<String, Integer> results;

    public Poll(String id, List<Question> questions) {
        this.id = id;
        this.questions = questions;
        this.results = new HashMap<>();
    }

    public void addVote(String voterId, String questionId, String answer) {
        if (results.containsKey(questionId)) {
            if (results.get(questionId).get(answer) != null) {
                results.get(questionId).put(answer, results.get(questionId).get(answer) + 1);
            } else {
                results.get(questionId).put(answer, 1);
            }
        } else {
            Map<String, Integer> answers = new HashMap<>();
            answers.put(answer, 1);
            results.put(questionId, answers);
        }
    }

    public void printResults() {
        for (Map.Entry<String, Map<String, Integer>> entry : results.entrySet()) {
            System.out.println("Question: " + entry.getKey());
            for (Map.Entry<String, Integer> answer : entry.getValue().entrySet()) {
                System.out.println(answer.getKey() + ": " + answer.getValue());
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        questions.add(new MultipleChoiceQuestion("q1", "multiple choice", Arrays.asList("A", "B", "C"), "A"));
        questions.add(new RankingQuestion("q2", "ranking", Arrays.asList("A", "B", "C", "D"), 4));

        Poll poll = new Poll("p1", questions);

        poll.addVote("v1", "q1", "A");
        poll.addVote("v1", "q2", "A");
        poll.addVote("v1", "q2", "B");

        poll.addVote("v2", "q1", "B");
        poll.addVote("v2", "q2", "A");
        poll.addVote("v2", "q2", "C");

        poll.addVote("v3", "q1", "C");
        poll.addVote("v3", "q2", "A");
        poll.addVote("v3", "q2", "B");
        poll.addVote("v3", "q2", "C");

        poll.printResults();
    }
}
```