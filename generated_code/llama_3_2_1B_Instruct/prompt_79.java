```java
import java.util.*;

class Employee {
    String id;
    String name;
    String department;
    Role role;
    double salary;

    public Employee(String id, String name, String department, Role role, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.role = role;
        this.salary = salary;
    }
}

class Role {
    String id;
    String name;

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Department {
    String id;
    String name;

    public Department(String id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Salary {
    double amount;

    public Salary(double amount) {
        this.amount = amount;
    }
}

class EmployeeManager {
    Map<String, Employee> employees = new HashMap<>();
    Map<String, Role> roles = new HashMap<>();
    Map<String, Department> departments = new HashMap<>();

    public void addEmployee(String id, String name, String department, Role role, double salary) {
        employees.put(id, new Employee(id, name, department, role, salary));
        departments.put(id, new Department(id, department));
        roles.put(id, new Role(id, role.name));
    }

    public void updateDepartment(String id, String department) {
        Department departmentObj = departments.get(id);
        departmentObj.name = department;
    }

    public void updateRole(String id, Role role) {
        Role roleObj = roles.get(id);
        roleObj.name = role.name;
    }

    public void updateSalary(String id, double salary) {
        Salary salaryObj = new Salary(salary);
        Employee employee = employees.get(id);
        employee.salary = salaryObj.amount;
    }

    public void displayEmployees() {
        for (Employee employee : employees.values()) {
            System.out.println("ID: " + employee.id + ", Name: " + employee.name + ", Department: " + employee.department + ", Role: " + employee.role.name + ", Salary: " + employee.salary);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();
        manager.addEmployee("1", "John Doe", "HR", "Manager", 5000);
        manager.addEmployee("2", "Jane Doe", "Marketing", "Intern", 3000);
        manager.addEmployee("3", "Bob Smith", "IT", "Developer", 6000);
        manager.updateDepartment("1", "Sales");
        manager.updateRole("1", new Role("1", "Manager"));
        manager.updateSalary("1", 5500);
        manager.displayEmployees();
    }
}
```