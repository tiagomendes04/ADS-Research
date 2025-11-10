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
        employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(String name) {
        employees.removeIf(employee -> employee.getName().equals(name));
    }

    public Employee findEmployeeByName(String name) {
        for (Employee employee : employees) {
            if (employee.getName().equals(name)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getDepartment().equals(department)) {
                result.add(employee);
            }
        }
        return result;
    }

    public void listAllEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        manager.addEmployee(new Employee("John Doe", "Developer", 75000, "IT"));
        manager.addEmployee(new Employee("Jane Smith", "Designer", 60000, "Marketing"));
        manager.addEmployee(new Employee("Alice Johnson", "Manager", 90000, "IT"));

        System.out.println("All Employees:");
        manager.listAllEmployees();

        System.out.println("\nEmployees in IT Department:");
        for (Employee e : manager.getEmployeesByDepartment("IT")) {
            System.out.println(e);
        }

        System.out.println("\nFinding John Doe:");
        Employee john = manager.findEmployeeByName("John Doe");
        if (john != null) {
            System.out.println(john);
        } else {
            System.out.println("Employee not found.");
        }

        System.out.println("\nRemoving Jane Smith:");
        manager.removeEmployee("Jane Smith");

        System.out.println("\nAll Employees after removal:");
        manager.listAllEmployees();
    }
}
```