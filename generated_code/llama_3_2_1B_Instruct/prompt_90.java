```java
import java.util.*;

class Student {
    String name;
    List<Course> courses;
    Map<String, Double> grades;

    public Student(String name) {
        this.name = name;
        this.courses = new ArrayList<>();
        this.grades = new HashMap<>();
    }
}

class Course {
    String name;
    int numStudents;

    public Course(String name) {
        this.name = name;
        this.numStudents = 0;
    }
}

class Grade {
    double score;

    public Grade(double score) {
        this.score = score;
    }
}

class Classroom {
    List<Student> students;
    List<Course> courses;

    public Classroom() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
    }
}

class ClassroomManagementSystem {
    Classroom classroom;

    public ClassroomManagementSystem() {
        this.classroom = new Classroom();
    }
}
```