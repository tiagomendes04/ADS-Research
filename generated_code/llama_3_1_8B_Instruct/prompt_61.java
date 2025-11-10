```java
// Student.java
public class Student {
    private String studentId;
    private String name;
    private boolean isProctored;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
        this.isProctored = false;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public boolean isProctored() {
        return isProctored;
    }

    public void setProctored(boolean proctored) {
        isProctored = proctored;
    }
}
```

```java
// Exam.java
import java.util.ArrayList;
import java.util.List;

public class Exam {
    private String examId;
    private String examName;
    private List<Student> students;
    private List<Question> questions;
    private List<Answer> answers;
    private String startTime;
    private String endTime;

    public Exam(String examId, String examName) {
        this.examId = examId;
        this.examName = examName;
        this.students = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    public String getExamId() {
        return examId;
    }

    public String getExamName() {
        return examName;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
```

```java
// Question.java
public class Question {
    private String questionId;
    private String questionText;
    private String type;

    public Question(String questionId, String questionText, String type) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.type = type;
    }

    public String getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getType() {
        return type;
    }
}
```

```java
// Answer.java
public class Answer {
    private String answerId;
    private String answerText;
    private String questionId;

    public Answer(String answerId, String answerText, String questionId) {
        this.answerId = answerId;
        this.answerText = answerText;
        this.questionId = questionId;
    }

    public String getAnswerId() {
        return answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public String getQuestionId() {
        return questionId;
    }
}
```

```java
// Proctor.java
public class Proctor {
    private String proctorId;
    private String name;

    public Proctor(String proctorId, String name) {
        this.proctorId = proctorId;
        this.name = name;
    }

    public String getProctorId() {
        return proctorId;
    }

    public String getName() {
        return name;
    }
}
```

```java
// ExamMonitor.java
import java.util.ArrayList;
import java.util.List;

public class ExamMonitor {
    private List<Exam> exams;
    private List<Proctor> proctors;

    public ExamMonitor() {
        this.exams = new ArrayList<>();
        this.proctors = new ArrayList<>();
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void addProctor(Proctor proctor) {
        proctors.add(proctor);
    }

    public void monitorExam(Exam exam) {
        System.out.println("Monitoring exam: " + exam.getExamName());
        for (Proctor proctor : proctors) {
            System.out.println("Proctor: " + proctor.getName());
        }
    }

    public void reportExam(Exam exam) {
        System.out.println("Exam report: " + exam.getExamName());
        for (Student student : exam.getStudents()) {
            System.out.println("Student: " + student.getName());
        }
    }
}
```

```java
// ExamReporter.java
import java.util.ArrayList;
import java.util.List;

public class ExamReporter {
    private List<Exam> exams;

    public ExamReporter() {
        this.exams = new ArrayList<>();
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void generateReport(Exam exam) {
        System.out.println("Exam report: " + exam.getExamName());
        for (Student student : exam.getStudents()) {
            System.out.println("Student: " + student.getName());
        }
    }
}
```

```java
// Main.java
public class Main {
    public static void main(String[] args) {
        // Create students
        Student student1 = new Student("S1", "John Doe");
        Student student2 = new Student("S2", "Jane Doe");

        // Create exam
        Exam exam = new Exam("E1", "Math Exam");
        exam.addStudent(student1);
        exam.addStudent(student2);

        // Create proctors
        Proctor proctor1 = new Proctor("P1", "Alice Smith");
        Proctor proctor2 = new Proctor("P2", "Bob Johnson");

        // Create monitor and reporter
        ExamMonitor monitor = new ExamMonitor();
        monitor.addExam(exam);
        monitor.addProctor(proctor1);
        monitor.addProctor(proctor2);
        monitor.monitorExam(exam);

        ExamReporter reporter = new ExamReporter();
        reporter.addExam(exam);
        reporter.generateReport(exam);
    }
}
```