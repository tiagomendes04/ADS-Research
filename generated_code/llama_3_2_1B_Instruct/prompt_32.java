```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Question {
    int id;
    String type;
    String question;
    int answer;
    int points;
    List<String> options;

    public Question(int id, String type, String question, int answer, int points, List<String> options) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.answer = answer;
        this.points = points;
        this.options = options;
    }
}

class Quiz {
    List<Question> questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public int calculateScore(Quiz quiz) {
        int totalPoints = 0;
        for (Question question : quiz.questions) {
            if (question.type.equals("multiple choice")) {
                int correctAnswer = 0;
                for (int i = 0; i < question.options.size(); i++) {
                    if (question.options.get(i).equals(question.question)) {
                        correctAnswer++;
                    }
                }
                totalPoints += correctAnswer * question.points;
            } else if (question.type.equals("true or false")) {
                totalPoints += 2;
            } else if (question.type.equals("open-ended")) {
                totalPoints += 1;
            }
        }
        return totalPoints;
    }
}

public class Main {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.addQuestion(new Question(1, "math", "What is the value of x?", 2, 1, List.of("2", "3", "4")));
        quiz.addQuestion(new Question(2, "math", "What is the value of x?", 2, 1, List.of("2", "3", "4")));
        quiz.addQuestion(new Question(3, "science", "What is the process by which plants make food?", 1, 2, List.of("respiration", "photosynthesis", "transpiration")));
        quiz.addQuestion(new Question(4, "science", "What is the process by which plants make food?", 1, 2, List.of("respiration", "photosynthesis", "transpiration")));
        quiz.addQuestion(new Question(5, "literature", "What is the name of the main character in Romeo and Juliet?", 1, 2, List.of("Tybalt", "Mercutio", "Juliet")));
        quiz.addQuestion(new Question(6, "literature", "What is the name of the main character in Romeo and Juliet?", 1, 2, List.of("Tybalt", "Mercutio", "Juliet")));
        System.out.println(quiz.calculateScore(quiz));
    }
}
```