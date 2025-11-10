```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class EmployeeManager {

    static HashMap<Integer, Employee> employees = new HashMap<>();
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            displayMenu();
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    viewEmployees();
                    break;
                case 3:
                    updateEmployee();
                    break;
                case 4:
                    removeEmployee();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    static void displayMenu() {
        System.out.println("Employee Management System");
        System.out.println("1. Add Employee");
        System.out.println("2. View Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Remove Employee");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    static void addEmployee() {
        System.out.println("Enter employee details:");
        System.out.print("ID: ");
        int id = input.nextInt();
        input.nextLine(); // consume newline
        System.out.print("Name: ");
        String name = input.nextLine();
        System.out.print("Role: ");
        String role = input.nextLine();
        System.out.print("Salary: ");
        double salary = input.nextDouble();
        System.out.print("Department: ");
        String department = input.nextLine();
        Employee employee = new Employee(id, name, role, salary, department);
        employees.put(id, employee);
        System.out.println("Employee added successfully!");
    }

    static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employees:");
            for (Employee employee : employees.values()) {
                System.out.println(employee);
            }
        }
    }

    static void updateEmployee() {
        System.out.println("Enter employee ID to update:");
        int id = input.nextInt();
        input.nextLine();
        if (employees.containsKey(id)) {
            Employee employee = employees.get(id);
            System.out.println("Current Employee Details:");
            System.out.println(employee);
            System.out.println("Enter new details:");
            System.out.print("Name: ");
            employee.setName(input.nextLine());
            System.out.print("Role: ");
            employee.setRole(input.nextLine());
            System.out.print("Salary: ");
            employee.setSalary(input.nextDouble());
            System.out.print("Department: ");
            employee.setDepartment(input.nextLine());
            System.out.println("Employee details updated successfully!");
        } else {
            System.out.println("Employee not found.");
        }
    }

    static void removeEmployee() {
        System.out.println("Enter employee ID to remove:");
        int id = input.nextInt();
        input.nextLine();
        if (employees.containsKey(id)) {
            employees.remove(id);
            System.out.println("Employee removed successfully!");
        } else {
            System.out.println("Employee not found.");
        }
    }
}

class Employee {
    private int id;
    private String name;
    private String role;
    private double salary;
    private String department;

    public Employee(int id, String name, String role, double salary, String department) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Role: " + role + ", Salary: " + salary + ", Department: " + department;
    }
}
```