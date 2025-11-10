**Budget.java**
```java
import java.math.BigDecimal;
import java.time.YearMonth;

public class Budget {
    private YearMonth month;
    private String category;
    private BigDecimal limit;
    private BigDecimal spent = BigDecimal.ZERO;

    public Budget(YearMonth month, String category, BigDecimal limit) {
        this.month = month;
        this.category = category;
        this.limit = limit;
    }

    public YearMonth getMonth() {
        return month;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void addExpense(BigDecimal amount) {
        spent = spent.add(amount);
    }

    public BigDecimal getRemaining() {
        return limit.subtract(spent);
    }
}
```

**Expense.java**
```java
import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {
    private LocalDate date;
    private String category;
    private String description;
    private BigDecimal amount;

    public Expense(LocalDate date, String category, String description, BigDecimal amount) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
```

**FinanceManager.java**
```java
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FinanceManager {
    private List<Budget> budgets = new ArrayList<>();
    private List<Expense> expenses = new ArrayList<>();
    private static final String DATA_FILE = "finance_data.json";

    public FinanceManager() {
        loadData();
    }

    public void addBudget(YearMonth month, String category, BigDecimal limit) {
        budgets.add(new Budget(month, category, limit));
    }

    public void addExpense(LocalDate date, String category, String description, BigDecimal amount) {
        Expense e = new Expense(date, category, description, amount);
        expenses.add(e);
        YearMonth ym = YearMonth.from(date);
        budgets.stream()
                .filter(b -> b.getMonth().equals(ym) && b.getCategory().equalsIgnoreCase(category))
                .findFirst()
                .ifPresent(b -> b.addExpense(amount));
    }

    public List<Budget> getBudgets() {
        return Collections.unmodifiableList(budgets);
    }

    public List<Expense> getExpenses() {
        return Collections.unmodifiableList(expenses);
    }

    public List<Expense> getExpensesByMonth(YearMonth month) {
        List<Expense> list = new ArrayList<>();
        for (Expense e : expenses) {
            if (YearMonth.from(e.getDate()).equals(month)) {
                list.add(e);
            }
        }
        return list;
    }

    public void generateMonthlyReport(YearMonth month) {
        System.out.println("=== Report for " + month + " ===");
        System.out.println("Budgets:");
        budgets.stream()
                .filter(b -> b.getMonth().equals(month))
                .forEach(b -> {
                    System.out.printf("- %s: Limit=%s, Spent=%s, Remaining=%s%n",
                            b.getCategory(),
                            b.getLimit(),
                            b.getSpent(),
                            b.getRemaining());
                });
        System.out.println("\nExpenses:");
        getExpensesByMonth(month).forEach(e -> {
            System.out.printf("- %s | %s | %s | %s%n",
                    e.getDate(),
                    e.getCategory(),
                    e.getDescription(),
                    e.getAmount());
        });
        System.out.println("==============================\n");
    }

    private void loadData() {
        File f = new File(DATA_FILE);
        if (!f.exists()) return;
        try (Reader r = new FileReader(f)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, ctx) ->
                            LocalDate.parse(json.getAsString()))
                    .registerTypeAdapter(YearMonth.class, (JsonDeserializer<YearMonth>) (json, type, ctx) ->
                            YearMonth.parse(json.getAsString()))
                    .registerTypeAdapter(BigDecimal.class, (JsonDeserializer<BigDecimal>) (json, type, ctx) ->
                            new BigDecimal(json.getAsString()))
                    .create();
            JsonObject obj = gson.fromJson(r, JsonObject.class);
            Type budgetListType = new TypeToken<ArrayList<Budget>>() {}.getType();
            Type expenseListType = new TypeToken<ArrayList<Expense>>() {}.getType();
            budgets = gson.fromJson(obj.get("budgets"), budgetListType);
            expenses = gson.fromJson(obj.get("expenses"), expenseListType);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveData() {
        try (Writer w = new FileWriter(DATA_FILE)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src, type, ctx) ->
                            new JsonPrimitive(src.toString()))
                    .registerTypeAdapter(YearMonth.class, (JsonSerializer<YearMonth>) (src, type, ctx) ->
                            new JsonPrimitive(src.toString()))
                    .registerTypeAdapter(BigDecimal.class, (JsonSerializer<BigDecimal>) (src, type, ctx) ->
                            new JsonPrimitive(src.toPlainString()))
                    .create();
            JsonObject obj = new JsonObject();
            obj.add("budgets", gson.toJsonTree(budgets));
            obj.add("expenses", gson.toJsonTree(expenses));
            gson.toJson(obj, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

**Main.java**
```java
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    private static final FinanceManager manager = new FinanceManager();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter YM_FMT = DateTimeFormatter.ofPattern("yyyy-MM");

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(manager::saveData));
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addBudget();
                case "2" -> addExpense();
                case "3" -> viewBudgets();
                case "4" -> viewExpenses();
                case "5" -> generateReport();
                case "0" -> {
                    manager.saveData();
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Personal Finance Tracker ===");
        System.out.println("1. Add Budget");
        System.out.println("2. Add Expense");
        System.out.println("3. View Budgets");
        System.out.println("4. View Expenses");
        System.out.println("5. Generate Monthly Report");
        System.out.println("0. Exit");
        System.out.print("Select: ");
    }

    private static void addBudget() {
        try {
            System.out.print("Month (yyyy-MM): ");
            YearMonth month = YearMonth.parse(scanner.nextLine().trim(), YM_FMT);
            System.out.print("Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Limit amount: ");
            BigDecimal limit = new BigDecimal(scanner.nextLine().trim());
            manager.addBudget(month, category, limit);
            System.out.println("Budget added.");
        } catch (Exception e) {
            System.out.println("Error adding budget: " + e.getMessage());
        }
    }

    private static void addExpense() {
        try {
            System.out.print("Date (yyyy-MM-dd): ");
            LocalDate date = LocalDate.parse(scanner.nextLine().trim(), DATE_FMT);
            System.out.print("Category: ");
            String category = scanner.nextLine().trim();
            System.out.print("Description: ");
            String description = scanner.nextLine().trim();
            System.out.print("Amount: ");
            BigDecimal amount = new BigDecimal(scanner.nextLine().trim());
            manager.addExpense(date, category, description, amount);
            System.out.println("Expense