```java
import java.util.*;

public class EmployeeManagementApp {
    public static void main(String[] args) {
        EmployeeService service = new EmployeeService();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Employee Management ---");
            System.out.println("1. Add Employee");
            System.out.println("2. List Employees");
            System.out.println("3. Update Salary");
            System.out.println("4. Transfer Department");
            System.out.println("5. Remove Employee");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addEmployee(service, scanner);
                    case "2" -> service.listEmployees().forEach(System.out::println);
                    case "3" -> updateSalary(service, scanner);
                    case "4" -> transferDepartment(service, scanner);
                    case "5" -> removeEmployee(service, scanner);
                    case "6" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addEmployee(EmployeeService service, Scanner scanner) {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Role (MANAGER, DEVELOPER, ANALYST, HR): ");
        Role role = Role.valueOf(scanner.nextLine().trim().toUpperCase());
        System.out.print("Enter Salary: ");
        double salary = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Enter Department: ");
        String deptName = scanner.nextLine().trim();
        Department dept = new Department(deptName);
        service.addEmployee(new Employee(id, name, role, salary, dept));
        System.out.println("Employee added.");
    }

    private static void updateSalary(EmployeeService service, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter New Salary: ");
        double salary = Double.parseDouble(scanner.nextLine().trim());
        service.updateSalary(id, salary);
        System.out.println("Salary updated.");
    }

    private static void transferDepartment(EmployeeService service, Scanner scanner) {
        System.out.print("Enter Employee ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter New Department: ");
        String deptName = scanner.nextLine().trim();
        service.transferDepartment(id, new Department(deptName));
        System.out.println("Department transferred.");
    }

    private static void removeEmployee(EmployeeService service, Scanner scanner) {
        System.out.print("Enter Employee ID to remove: ");
        String id = scanner.nextLine().trim();
        service.removeEmployee(id);
        System.out.println("Employee removed.");
    }
}

/* ==================== Domain ==================== */
enum Role {
    MANAGER, DEVELOPER, ANALYST, HR
}

class Department {
    private final String name;
    public Department(String name) { this.name = name; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(name, that.name);
    }
    @Override public int hashCode() { return Objects.hash(name); }
}

class Employee {
    private final String id;
    private String name;
    private Role role;
    private double salary;
    private Department department;

    public Employee(String id, String name, Role role, double salary, Department department) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.salary = salary;
        this.department = department;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Role getRole() { return role; }
    public double getSalary() { return salary; }
    public Department getDepartment() { return department; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(Department department) { this.department = department; }

    @Override public String toString() {
        return String.format("ID:%s | Name:%s | Role:%s | Salary:%.2f | Dept:%s",
                id, name, role, salary, department);
    }
}

/* ==================== Service ==================== */
class EmployeeService {
    private final Map<String, Employee> employees = new HashMap<>();

    public void addEmployee(Employee emp) {
        if (employees.containsKey(emp.getId())) {
            throw new IllegalArgumentException("Employee ID already exists.");
        }
        employees.put(emp.getId(), emp);
    }

    public List<Employee> listEmployees() {
        return new ArrayList<>(employees.values());
    }

    public void updateSalary(String id, double newSalary) {
        Employee emp = getEmployee(id);
        if (newSalary < 0) throw new IllegalArgumentException("Salary cannot be negative.");
        emp.setSalary(newSalary);
    }

    public void transferDepartment(String id, Department newDept) {
        Employee emp = getEmployee(id);
        emp.setDepartment(newDept);
    }

    public void removeEmployee(String id) {
        if (employees.remove(id) == null) {
            throw new NoSuchElementException("Employee not found.");
        }
    }

    private Employee getEmployee(String id) {
        Employee emp = employees.get(id);
        if (emp == null) throw new NoSuchElementException("Employee not found.");
        return emp;
    }
}
```