```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamProctor {
    private Map<String, Proctor> students;
    private Map<String, Student> studentsWithScores;
    private List<ExamQuestion> examQuestions;

    public ExamProctor() {
        students = new HashMap<>();
        studentsWithScores = new HashMap<>();
        examQuestions = new ArrayList<>();
    }

    public void addStudent(String studentId) {
        students.put(studentId, new Student(studentId));
    }

    public void addQuestion(ExamQuestion question) {
        examQuestions.add(question);
    }

    public void markQuestion(String studentId, String questionId, int score) {
        Proctor proctor = students.get(studentId);
        if (proctor != null) {
            examQuestions.get(questionId).mark(proctor, score);
            studentsWithScores.put(studentId, proctor.getScore());
        }
    }

    public void reportExam(String studentId) {
        Proctor proctor = students.get(studentId);
        if (proctor != null) {
            System.out.println("Student " + studentId + " completed all questions");
            for (ExamQuestion question : examQuestions) {
                System.out.println(question.toString());
            }
            System.out.println("Student " + studentId + " scored " + proctor.getScore() + " out of 10");
            studentsWithScores.put(studentId, proctor.getScore());
        }
    }

    public static void main(String[] args) {
        ExamProctor proctor = new ExamProctor();
        proctor.addStudent("123");
        proctor.addQuestion(new ExamQuestion("Question 1", 5));
        proctor.addQuestion(new ExamQuestion("Question 2", 8));
        proctor.markQuestion("123", "1", 5);
        proctor.reportExam("123");
    }
}

class Student {
    private String id;
    private int score;

    public Student(String id) {
        this.id = id;
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void mark(Proctor proctor, int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", score=" + score +
                '}';
    }
}

class ExamQuestion {
    private String id;
    private int points;

    public ExamQuestion(String id, int points) {
        this.id = id;
        this.points = points;
    }

    @Override
    public String toString() {
        return "ExamQuestion{" +
                "id='" + id + '\'' +
                ", points=" + points +
                '}';
    }
}

class Proctor {
    private String id;

    public Proctor(String id) {
        this.id = id;
    }

    public int getScore() {
        return 10;
    }

    @Override
    public String toString() {
        return "Proctor{" +
                "id='" + id + '\'' +
                '}';
    }
}
```