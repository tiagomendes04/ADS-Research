```java
// Student.java

public class Student {
    private String id;
    private String name;
    private String email;

    public Student(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

// Course.java

public class Course {
    private String id;
    private String name;
    private String description;

    public Course(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}

// Grade.java

public class Grade {
    private String studentId;
    private String courseId;
    private double score;

    public Grade(String studentId, String courseId, double score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public double getScore() {
        return score;
    }
}

// Classroom.java

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private String id;
    private String name;
    private List<Student> students;
    private List<Course> courses;
    private List<Grade> grades;

    public Classroom(String id, String name) {
        this.id = id;
        this.name = name;
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Grade> getGrades() {
        return grades;
    }
}

// ClassroomManager.java

import java.util.Scanner;

public class ClassroomManager {
    private Classroom classroom;

    public ClassroomManager(Classroom classroom) {
        this.classroom = classroom;
    }

    public void manageClassroom() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Add Grade");
            System.out.println("4. Display Students");
            System.out.println("5. Display Courses");
            System.out.println("6. Display Grades");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            switch (option) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    addCourse(scanner);
                    break;
                case 3:
                    addGrade(scanner);
                    break;
                case 4:
                    displayStudents();
                    break;
                case 5:
                    displayCourses();
                    break;
                case 6:
                    displayGrades();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private void addStudent(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String id = scanner.next();
        System.out.print("Enter student name: ");
        String name = scanner.next();
        System.out.print("Enter student email: ");
        String email = scanner.next();
        Student student = new Student(id, name, email);
        classroom.addStudent(student);
        System.out.println("Student added successfully!");
    }

    private void addCourse(Scanner scanner) {
        System.out.print("Enter course ID: ");
        String id = scanner.next();
        System.out.print("Enter course name: ");
        String name = scanner.next();
        System.out.print("Enter course description: ");
        String description = scanner.next();
        Course course = new Course(id, name, description);
        classroom.addCourse(course);
        System.out.println("Course added successfully!");
    }

    private void addGrade(Scanner scanner) {
        System.out.print("Enter student ID: ");
        String studentId = scanner.next();
        System.out.print("Enter course ID: ");
        String courseId = scanner.next();
        System.out.print("Enter grade score: ");
        double score = scanner.nextDouble();
        Grade grade = new Grade(studentId, courseId, score);
        classroom.addGrade(grade);
        System.out.println("Grade added successfully!");
    }

    private void displayStudents() {
        System.out.println("Students:");
        for (Student student : classroom.getStudents()) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + ", Email: " + student.getEmail());
        }
    }

    private void displayCourses() {
        System.out.println("Courses:");
        for (Course course : classroom.getCourses()) {
            System.out.println("ID: " + course.getId() + ", Name: " + course.getName() + ", Description: " + course.getDescription());
        }
    }

    private void displayGrades() {
        System.out.println("Grades:");
        for (Grade grade : classroom.getGrades()) {
            System.out.println("Student ID: " + grade.getStudentId() + ", Course ID: " + grade.getCourseId() + ", Score: " + grade.getScore());
        }
    }

    public static void main(String[] args) {
        Classroom classroom = new Classroom("CL-001", "Classroom 1");
        ClassroomManager manager = new ClassroomManager(classroom);
        manager.manageClassroom();
    }
}
```