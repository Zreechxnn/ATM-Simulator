package ATM;

public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private static int transactionCount = 0;

    public BankAccount(String accountNumber, String accountHolder) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = 0.0;
    }

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this(accountNumber, accountHolder);
        if (initialBalance > 0) {
            this.balance = initialBalance;
        }
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionCount++;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionCount++;
            return true;
        }
        return false;
    }

    public static int getTransactionCount() {
        return transactionCount;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }
}