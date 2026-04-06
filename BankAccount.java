import java.util.ArrayList;

/*
 * Requirement: Each account should have:
 *   - Account number
 *   - Account holder name
 *   - Balance
 *
 * Requirement: Maintain transaction history for each account
 * Requirement: Use ArrayList for transaction history
 * Requirement: Apply encapsulation (private fields + getters/setters)
 */
public class BankAccount {

    // ── Private fields (Encapsulation) ──────────────────────────────────────
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private ArrayList<String> transactionHistory;  // Requirement: ArrayList for history

    // ── Constructor ──────────────────────────────────────────────────────────
    public BankAccount(String accountNumber, String accountHolderName, double initialBalance) {
        this.accountNumber     = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance           = initialBalance;
        this.transactionHistory = new ArrayList<>();
        transactionHistory.add("Account created with initial balance: Rs." + String.format("%.2f", initialBalance));
    }

    // ── Getters (Encapsulation) ──────────────────────────────────────────────
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    // ── Setters (Encapsulation) ──────────────────────────────────────────────
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    // ── Banking Operations ────────────────────────────────────────────────────

    // Requirement: Deposit money into an account
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be greater than zero.");
        }
        balance += amount;
        transactionHistory.add("Deposited: Rs." + String.format("%.2f", amount)
                + "  |  Balance: Rs." + String.format("%.2f", balance));
        System.out.println("Rs." + String.format("%.2f", amount) + " deposited successfully.");
        System.out.println("Updated Balance: Rs." + String.format("%.2f", balance));
    }

    // Requirement: Withdraw money from an account (with insufficient balance handling)
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                "Insufficient balance! Available: Rs." + String.format("%.2f", balance)
                + ", Requested: Rs." + String.format("%.2f", amount)
            );
        }
        balance -= amount;
        transactionHistory.add("Withdrawn: Rs." + String.format("%.2f", amount)
                + "  |  Balance: Rs." + String.format("%.2f", balance));
        System.out.println("Rs." + String.format("%.2f", amount) + " withdrawn successfully.");
        System.out.println("Updated Balance: Rs." + String.format("%.2f", balance));
    }

    // Requirement: Check account balance
    public void checkBalance() {
        System.out.println("Account Holder : " + accountHolderName);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Current Balance: Rs." + String.format("%.2f", balance));
    }

    // Requirement: View transaction history
    public void viewTransactionHistory() {
        System.out.println("-------- Transaction History for Account: " + accountNumber + " --------");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (int i = 0; i < transactionHistory.size(); i++) {
                System.out.println((i + 1) + ". " + transactionHistory.get(i));
            }
        }
        System.out.println("-----------------------------------------------------------");
    }
}
