```java
// Employee.java
public class Employee {
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

    public void setId(int id) {
        this.id = id;
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}

// Department.java
public class Department {
    private String name;
    private String location;
    private Employee[] employees;

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
        this.employees = new Employee[0];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addEmployee(Employee employee) {
        Employee[] newEmployees = new Employee[employees.length + 1];
        System.arraycopy(employees, 0, newEmployees, 0, employees.length);
        newEmployees[employees.length] = employee;
        employees = newEmployees;
    }

    public void removeEmployee(Employee employee) {
        Employee[] newEmployees = new Employee[employees.length - 1];
        int j = 0;
        for (Employee e : employees) {
            if (e != employee) {
                newEmployees[j++] = e;
            }
        }
        employees = newEmployees;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    @Override
    public String toString() {
        return "Department{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", employees=" + employees.length +
                '}';
    }
}

// EmployeeManager.java
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private List<Employee> employees;
    private List<Department> departments;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
        this.departments = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void printEmployees() {
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    public void printDepartments() {
        for (Department department : departments) {
            System.out.println(department);
            for (Employee employee : department.getEmployees()) {
                System.out.println(employee);
            }
        }
    }

    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();

        Employee employee1 = new Employee(1, "John Doe", "Manager", 50000, "Sales");
        Employee employee2 = new Employee(2, "Jane Doe", "Developer", 40000, "IT");
        Employee employee3 = new Employee(3, "Bob Smith", "Salesperson", 30000, "Sales");

        Department department1 = new Department("Sales", "New York");
        Department department2 = new Department("IT", "Chicago");

        manager.addEmployee(employee1);
        manager.addEmployee(employee2);
        manager.addEmployee(employee3);

        manager.addDepartment(department1);
        manager.addDepartment(department2);

        department1.addEmployee(employee1);
        department1.addEmployee(employee3);

        department2.addEmployee(employee2);

        manager.printEmployees();
        System.out.println();
        manager.printDepartments();
    }
}
```