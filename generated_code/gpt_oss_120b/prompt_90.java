```java
package com.example.classroom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* ===================== Student ===================== */
public class Student {
    private final String studentId;
    private String firstName;
    private String lastName;
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Student(String studentId, String firstName, String lastName) {
        this.studentId = Objects.requireNonNull(studentId);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getStudentId() { return studentId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    public void enroll(Course course) {
        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    public void assignGrade(Course course, double score) {
        enrollments.stream()
                .filter(e -> e.getCourse().equals(course))
                .findFirst()
                .ifPresent(e -> e.setGrade(new Grade(score)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return studentId.equals(student.studentId);
    }

    @Override
    public int hashCode() { return studentId.hashCode(); }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + firstName + ' ' + lastName + '\'' +
                '}';
    }
}

/* ===================== Course ===================== */
public class Course {
    private final String courseCode;
    private String title;
    private final List<Enrollment> enrollments = new ArrayList<>();

    public Course(String courseCode, String title) {
        this.courseCode = Objects.requireNonNull(courseCode);
        this.title = title;
    }

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<Enrollment> getEnrollments() {
        return Collections.unmodifiableList(enrollments);
    }

    void addEnrollment(Enrollment enrollment) {
        if (!enrollments.contains(enrollment)) {
            enrollments.add(enrollment);
        }
    }

    public List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (Enrollment e : enrollments) {
            students.add(e.getStudent());
        }
        return students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;
        return courseCode.equals(course.courseCode);
    }

    @Override
    public int hashCode() { return courseCode.hashCode(); }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}

/* ===================== Enrollment ===================== */
public class Enrollment {
    private final Student student;
    private final Course course;
    private Grade grade;

    public Enrollment(Student student, Course course) {
        this.student = Objects.requireNonNull(student);
        this.course = Objects.requireNonNull(course);
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public Grade getGrade() { return grade; }
    public void setGrade(Grade grade) { this.grade = grade; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enrollment that = (Enrollment) o;
        return student.equals(that.student) && course.equals(that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student.getStudentId() +
                ", course=" + course.getCourseCode() +
                ", grade=" + (grade != null ? grade.getScore() : "N/A") +
                '}';
    }
}

/* ===================== Grade ===================== */
public class Grade {
    private final double score; // 0.0 - 100.0

    public Grade(double score) {
        if (score < 0.0 || score > 100.0) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.score = score;
    }

    public double getScore() { return score; }

    public String getLetterGrade() {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    @Override
    public String toString() {
        return "Grade{" +
                "score=" + score +
                ", letter='" + getLetterGrade() + '\'' +
                '}';
    }
}

/* ===================== ClassroomManager ===================== */
public class ClassroomManager {
    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();

    public Student addStudent(String studentId, String firstName, String lastName) {
        Student s = new Student(studentId, firstName, lastName);
        students.add(s);
        return s;
    }

    public Course addCourse(String courseCode, String title) {
        Course c = new Course(courseCode, title);
        courses.add(c);
        return c;
    }

    public Student findStudentById(String studentId) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public Course findCourseByCode(String courseCode) {
        return courses.stream()
                .filter(c -> c.getCourseCode().equals(courseCode))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courses);
    }
}
```