```java
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BankingSystem {

    public static void main(String[] args) {
        Bank bank = new Bank("My Bank");

        Customer customer1 = bank.createCustomer("Alice", "Smith");
        Customer customer2 = bank.createCustomer("Bob", "Johnson");

        Account account1 = bank.createAccount(customer1, AccountType.CHECKING);
        Account account2 = bank.createAccount(customer1, AccountType.SAVINGS);
        Account account3 = bank.createAccount(customer2, AccountType.CHECKING);


        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);


        account1.deposit(1000);
        account2.deposit(500);
        account3.deposit(2000);

        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);

        try {
            account1.withdraw(200);
            account2.withdraw(600);  // Should throw exception
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(account1);
        System.out.println(account2);

        try {
            account1.transfer(300, account3);
        } catch (InsufficientFundsException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(account1);
        System.out.println(account3);

        System.out.println(bank);
    }
}


class Bank {
    private String name;
    private List<Customer> customers;
    private List<Account> accounts;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Customer createCustomer(String firstName, String lastName) {
        Customer customer = new Customer(firstName, lastName);
        customers.add(customer);
        return customer;
    }

    public Account createAccount(Customer customer, AccountType type) {
        Account account = new Account(generateAccountNumber(), customer, type);
        accounts.add(account);
        return account;
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customers); // Return a copy
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts); // Return a copy
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                ", customers=" + customers +
                ", accounts=" + accounts +
                '}';
    }
}

class Customer {
    private String firstName;
    private String lastName;
    private String customerId;
    private static int nextCustomerId = 1;

    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.customerId = String.format("CUST-%04d", nextCustomerId++);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", customerId='" + customerId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId);
    }
}


enum AccountType {
    CHECKING,
    SAVINGS
}


class Account {
    private String accountNumber;
    private Customer accountHolder;
    private double balance;
    private AccountType type;

    public Account(String accountNumber, Customer accountHolder, AccountType type) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
        } else {
            throw new InsufficientFundsException("Insufficient funds in account " + accountNumber);
        }
    }

    public void transfer(double amount, Account recipient) throws InsufficientFundsException {
        withdraw(amount);
        recipient.deposit(amount);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountHolder=" + accountHolder +
                ", balance=" + balance +
                ", type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}


class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
```