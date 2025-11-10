```java
import java.util.ArrayList;
import java.util.List;

class Employee {
    private String name;
    private String role;
    private double salary;
    private String department;

    public Employee(String name, String role, double salary, String department) {
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}

class EmployeeManager {
    private List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(String name) {
        employees.removeIf(employee -> employee.getName().equals(name));
    }

    public List<Employee> getEmployeesInDepartment(String department) {
        return employees.stream()
                .filter(employee -> employee.getDepartment().equals(department))
                .toList();
    }

    public double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

    public List<Employee> getEmployeesByRole(String role) {
        return employees.stream()
                .filter(employee -> employee.getRole().equals(role))
                .toList();
    }

    @Override
    public String toString() {
        return "EmployeeManager{" +
                "employees=" + employees +
                '}';
    }
}

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        manager.addEmployee(new Employee("Alice", "Manager", 90000, "HR"));
        manager.addEmployee(new Employee("Bob", "Developer", 70000, "Tech"));
        manager.addEmployee(new Employee("Charlie", "Analyst", 55000, "Finance"));
        manager.addEmployee(new Employee("Diana", "Developer", 75000, "Tech"));

        System.out.println(manager);
        System.out.println("Employees in Tech: " + manager.getEmployeesInDepartment("Tech"));
        System.out.println("Total Salary: " + manager.calculateTotalSalary());
        System.out.println("Developers: " + manager.getEmployeesByRole("Developer"));
    }
}
```