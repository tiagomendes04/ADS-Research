```java
import java.util.ArrayList;
import java.util.List;

class PollQuestion {
    private String questionText;
    private List<String> options;

    public PollQuestion(String questionText) {
        this.questionText = questionText;
        this.options = new ArrayList<>();
    }

    public void addOption(String option) {
        this.options.add(option);
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }
}

class RankingQuestion extends PollQuestion {
    private List<String> rankingOrder;

    public RankingQuestion(String questionText) {
        super(questionText);
        this.rankingOrder = new ArrayList<>();
    }

    public void addRankingOption(String option) {
        this.options.add(option);
        this.rankingOrder.add(option);
    }

    public void setRankingOrder(List<String> rankingOrder) {
        this.rankingOrder = rankingOrder;
    }

    public List<String> getRankingOrder() {
        return rankingOrder;
    }
}

class PollSystem {
    private List<PollQuestion> questions;

    public PollSystem() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(PollQuestion question) {
        this.questions.add(question);
    }

    public void displayQuestions() {
        for (PollQuestion question : questions) {
            System.out.println(question.getQuestionText());
            for (String option : question.getOptions()) {
                System.out.println("- " + option);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        PollSystem system = new PollSystem();

        PollQuestion mcqQuestion = new PollQuestion("What is your favorite programming language?");
        mcqQuestion.addOption("Java");
        mcqQuestion.addOption("Python");
        mcqQuestion.addOption("C++");
        mcqQuestion.addOption("JavaScript");
        system.addQuestion(mcqQuestion);

        RankingQuestion rankingQuestion = new RankingQuestion("Rank the following languages from most to least favorite:");
        rankingQuestion.addRankingOption("Java");
        rankingQuestion.addRankingOption("Python");
        rankingQuestion.addRankingOption("C++");
        rankingQuestion.addRankingOption("JavaScript");
        system.addQuestion(rankingQuestion);

        system.displayQuestions();
    }
}
```