```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Transaction {
    int id;
    double amount;
    String description;
    String date;
    String type;

    public Transaction(int id, double amount, String description, String date, String type) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}

class Budget {
    int id;
    String name;
    double target;
    Map<String, Double> transactions;

    public Budget(int id, String name, double target, Map<String, Double> transactions) {
        this.id = id;
        this.name = name;
        this.target = target;
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", target=" + target +
                ", transactions=" + transactions +
                '}';
    }
}

class Expense {
    int id;
    String name;
    double amount;
    String date;

    public Expense(int id, String name, double amount, String date) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                '}';
    }
}

class Report {
    int id;
    String date;
    String budget;
    Map<String, Double> expenses;
    Map<String, Double> total;
    int cash;

    public Report(int id, String date, String budget, Map<String, Double> expenses, Map<String, Double> total, int cash) {
        this.id = id;
        this.date = date;
        this.budget = budget;
        this.expenses = expenses;
        this.total = total;
        this.cash = cash;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", budget='" + budget + '\'' +
                ", expenses=" + expenses +
                ", total=" + total +
                ", cash=" + cash +
                '}';
    }
}

public class FinanceTracker {
    static class Transactions {
        List<Transaction> transactions = new ArrayList<>();

        public void addTransaction(Transaction transaction) {
            transactions.add(transaction);
        }
    }

    static class Budgets {
        List<Budget> budgets = new ArrayList<>();

        public void addBudget(Budget budget) {
            budgets.add(budget);
        }
    }

    static class Expenses {
        List<Expense> expenses = new ArrayList<>();

        public void addExpense(Expense expense) {
            expenses.add(expense);
        }
    }

    static class Reports {
        List<Report> reports = new ArrayList<>();

        public void addReport(Report report) {
            reports.add(report);
        }
    }

    public static void main(String[] args) {
        Transactions transactions = new Transactions();
        Budgets budgets = new Budgets();
        Expenses expenses = new Expenses();
        Reports reports = new Reports();

        budgets.addBudget(new Budget(1, "Personal", 10000, transactions));

        transactions.addTransaction(new Transaction(1, 1000, "Rent", "2023-03-01", "Expense"));
        transactions.addTransaction(new Transaction(2, 2000, "Food", "2023-03-01", "Expense"));
        transactions.addTransaction(new Transaction(3, 500, "Utilities", "2023-03-01", "Expense"));

        budgets.addBudget(new Budget(2, "Housing", 5000, transactions));

        expenses.addExpense(new Expense(1, "Rent", 1000, "2023-03-01"));
        expenses.addExpense(new Expense(2, "Food", 2000, "2023-03-01"));
        expenses.addExpense(new Expense(3, "Utilities", 500, "2023-03-01"));

        reports.addReport(new Report(1, "2023-03-01", "Personal", expenses, new HashMap<>(), 10000));
        reports.addReport(new Report(2, "2023-03-01", "Housing", expenses, new HashMap<>(), 5000));
        reports.addReport(new Report(3, "2023-03-01", "Other", expenses, new HashMap<>(), 1000));

        for (Transaction transaction : transactions.transactions) {
            System.out.println(transaction);
        }

        for (Budget budget : budgets.budgets) {
            System.out.println(budget);
        }

        for (Expense expense : expenses.expenses) {
            System.out.println(expense);
        }

        for (Report report : reports.reports) {
            System.out.println(report);
        }
    }
}
```