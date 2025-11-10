```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Student {
    String name;
    int studentId;
    Map<String, Double> grades;

    public Student(String name, int studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new HashMap<>();
    }
}

class Course {
    String name;
    List<Student> students;

    public Course(String name) {
        this.name = name;
        this.students = new ArrayList<>();
    }
}

class Grade {
    double score;
    String courseName;
    String studentName;

    public Grade(double score, String courseName, String studentName) {
        this.score = score;
        this.courseName = courseName;
        this.studentName = studentName;
    }
}
```