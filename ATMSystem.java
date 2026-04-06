import java.util.HashMap;
import java.util.Scanner;

/*
 * ATM Simulation System
 * =====================
 * Requirements covered:
 *  [1] Multiple accounts stored using HashMap
 *  [2] Each account: accountNumber, accountHolderName, balance
 *  [3] Operations: create, deposit, withdraw, check balance, transaction history
 *  [4] ArrayList for transaction history (inside BankAccount)
 *  [5] HashMap for storing accounts
 *  [6] Encapsulation: private fields + getters/setters (inside BankAccount)
 *  [7] Exception handling: custom exceptions + try-catch
 *  [8] Scanner for user input
 *  [9] No database (in-memory only)
 *  [10] Pure Java only
 *  [11] Input validation
 */
public class ATMSystem {

    // Requirement [1] [5]: HashMap to store multiple accounts
    // Key = Account Number (String), Value = BankAccount object
    static HashMap<String, BankAccount> accounts = new HashMap<>();

    static Scanner scanner = new Scanner(System.in);
    static int accountCounter = 1001;  // Auto-generates account numbers: 1001, 1002, ...

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("       WELCOME TO ATM SIMULATION SYSTEM    ");
        System.out.println("===========================================");

        boolean running = true;

        while (running) {
            System.out.println("\n----------- MAIN MENU -----------");
            System.out.println("1. Create New Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Check Balance");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.println("---------------------------------");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    viewTransactionHistory();
                    break;
                case 6:
                    running = false;
                    System.out.println("\nThank you for using ATM Simulation System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice! Please enter a number between 1 and 6.");
            }
        }

        scanner.close();
    }

    // ── Operation 1: Create New Account ──────────────────────────────────────
    static void createAccount() {
        System.out.println("\n--- Create New Account ---");

        System.out.print("Enter Account Holder Name: ");
        String name = scanner.nextLine().trim();

        // Requirement [11]: Input validation - name cannot be blank
        if (name.isEmpty()) {
            System.out.println("Error: Name cannot be empty.");
            return;
        }

        System.out.print("Enter Initial Deposit Amount (Rs.): ");
        double initialDeposit = readDouble();

        // Requirement [11]: Input validation - initial deposit cannot be negative
        if (initialDeposit < 0) {
            System.out.println("Error: Initial deposit cannot be negative.");
            return;
        }

        // Generate unique account number
        String accountNumber = "ACC" + accountCounter++;

        // Create BankAccount object and store in HashMap
        BankAccount newAccount = new BankAccount(accountNumber, name, initialDeposit);
        accounts.put(accountNumber, newAccount);   // Requirement [1]: Store in HashMap

        System.out.println("\nAccount created successfully!");
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + name);
        System.out.println("Initial Balance: Rs." + String.format("%.2f", initialDeposit));
        System.out.println("Please remember your Account Number: " + accountNumber);
    }

    // ── Operation 2: Deposit Money ────────────────────────────────────────────
    static void depositMoney() {
        System.out.println("\n--- Deposit Money ---");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine().trim();

        // Requirement [7] [11]: Exception handling for invalid account
        try {
            BankAccount account = getAccount(accNo);   // throws AccountNotFoundException

            System.out.print("Enter Amount to Deposit (Rs.): ");
            double amount = readDouble();

            account.deposit(amount);   // throws InvalidAmountException

        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── Operation 3: Withdraw Money ───────────────────────────────────────────
    static void withdrawMoney() {
        System.out.println("\n--- Withdraw Money ---");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine().trim();

        // Requirement [7]: Exception handling for invalid account, invalid amount, insufficient balance
        try {
            BankAccount account = getAccount(accNo);   // throws AccountNotFoundException

            System.out.print("Enter Amount to Withdraw (Rs.): ");
            double amount = readDouble();

            account.withdraw(amount);  // throws InvalidAmountException or InsufficientBalanceException

        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── Operation 4: Check Balance ────────────────────────────────────────────
    static void checkBalance() {
        System.out.println("\n--- Check Balance ---");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine().trim();

        try {
            BankAccount account = getAccount(accNo);
            System.out.println();
            account.checkBalance();

        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── Operation 5: View Transaction History ────────────────────────────────
    static void viewTransactionHistory() {
        System.out.println("\n--- View Transaction History ---");
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine().trim();

        try {
            BankAccount account = getAccount(accNo);
            System.out.println();
            account.viewTransactionHistory();

        } catch (AccountNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ── Helper: Look up account from HashMap ──────────────────────────────────
    // Requirement [7]: Throws AccountNotFoundException if account not found
    static BankAccount getAccount(String accountNumber) throws AccountNotFoundException {
        // Requirement [11]: Validate account number input
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new AccountNotFoundException("Account number cannot be empty.");
        }

        BankAccount account = accounts.get(accountNumber);  // HashMap lookup O(1)

        if (account == null) {
            throw new AccountNotFoundException("No account found with account number: " + accountNumber);
        }

        return account;
    }

    // ── Helper: Safe integer input via Scanner ────────────────────────────────
    // Requirement [11]: Handles non-integer input
    static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    // ── Helper: Safe double input via Scanner ─────────────────────────────────
    // Requirement [11]: Handles non-numeric input
    static double readDouble() {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid amount: ");
            }
        }
    }
}
