```java
import java.util.*;

class Question {
    String id;
    String title;
    String type;
    List<Option> options;

    public Question(String id, String title, String type) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.options = new ArrayList<>();
    }

    public void addOption(Option option) {
        this.options.add(option);
    }
}

class Option {
    String id;
    String text;

    public Option(String id, String text) {
        this.id = id;
        this.text = text;
    }
}

class VotingSystem {
    Map<String, Question> questions;
    Map<String, Ranking> rankings;

    public VotingSystem() {
        this.questions = new HashMap<>();
        this.rankings = new HashMap<>();
    }

    public void addQuestion(Question question) {
        this.questions.put(question.id, question);
    }

    public void addRanking(Ranking ranking) {
        this.rankings.put(ranking.id, ranking);
    }

    public void vote(String questionId, String answerId, int ranking) {
        Question question = questions.get(questionId);
        if (question != null) {
            if (question.type.equals("multiple-choice")) {
                Option option = new Option(answerId, "");
                question.addOption(option);
            } else if (question.type.equals("ranking")) {
                Ranking rankingQ = new Ranking(questionId, ranking);
                this.rankings.put(questionId, rankingQ);
            }
        }
    }

    public void printResults() {
        for (Map.Entry<String, Question> entry : questions.entrySet()) {
            System.out.println("Question: " + entry.getValue().title);
            if (entry.getValue().type.equals("multiple-choice")) {
                for (Option option : entry.getValue().options) {
                    System.out.println("  Option: " + option.text + " (" + option.id + ")");
                }
            } else if (entry.getValue().type.equals("ranking")) {
                Ranking ranking = rankings.get(entry.getKey());
                if (ranking != null) {
                    System.out.println("  Ranking: " + ranking.value);
                }
            }
        }
    }
}

class Ranking {
    String id;
    int value;

    public Ranking(String id, int value) {
        this.id = id;
        this.value = value;
    }
}

public class Main {
    public static void main(String[] args) {
        VotingSystem system = new VotingSystem();

        Question question1 = new Question("1", "What is your favorite color?", "multiple-choice");
        question1.addOption(new Option("1", "Red"));
        question1.addOption(new Option("2", "Blue"));
        question1.addOption(new Option("3", "Green"));
        system.addQuestion(question1);

        Ranking ranking1 = new Ranking("1", 2);
        system.addRanking(ranking1);

        Question question2 = new Question("2", "Rank your favorite fruits?", "ranking");
        system.addQuestion(question2);

        system.vote("1", "1", 1);
        system.vote("1", "2", 2);
        system.vote("1", "3", 3);
        system.vote("2", "1", 2);
        system.vote("2", "2", 3);
        system.vote("2", "3", 1);

        system.printResults();
    }
}
```