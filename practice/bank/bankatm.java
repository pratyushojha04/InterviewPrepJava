//package practice.bank;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class BankATM {
    private static Map<String, BankAccount> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    
    static {
        // Initialize sample accounts
        accounts.put("1001", new BankAccount("1001", "Pratyush", 5000));
        accounts.put("1002", new BankAccount("1002", "John", 10000));
    }
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Bank ATM ===");
        
        while (true) {
            System.out.print("Enter Account Number (or 'exit' to quit): ");
            String accountNum = scanner.nextLine().trim();
            
            if (accountNum.equalsIgnoreCase("exit")) {
                System.out.println("Thank you for using Bank ATM. Goodbye!");
                break;
            }
            
            BankAccount account = accounts.get(accountNum);
            if (account == null) {
                System.out.println("Invalid account number. Please try again.\n");
                continue;
            }
            
            authenticate(account);
        }
        scanner.close();
    }
    
    private static void authenticate(BankAccount account) {
        System.out.print("Enter PIN (default: 1234): ");
        String pin = scanner.nextLine().trim();
        
        if (!pin.equals("1234")) {
            System.out.println("Invalid PIN. Access Denied.\n");
            return;
        }
        
        showMenu(account);
    }
    
    private static void showMenu(BankAccount account) {
        while (true) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. View Transaction History");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    checkBalance(account);
                    break;
                case "2":
                    withdraw(account);
                    break;
                case "3":
                    deposit(account);
                    break;
                case "4":
                    viewTransactionHistory(account);
                    break;
                case "5":
                    System.out.println("Returning to main menu...\n");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    private static void checkBalance(BankAccount account) {
        System.out.println("\n--- Balance Information ---");
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.println("Account Number: " + account.getAccountNumber());
        System.out.println("Current Balance: $" + account.getBalance());
    }
    
    private static void withdraw(BankAccount account) {
        System.out.print("\nEnter amount to withdraw: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Invalid amount. Must be greater than 0.");
                return;
            }
            if (amount > account.getBalance()) {
                System.out.println("Insufficient funds. Your balance: $" + account.getBalance());
                return;
            }
            account.withdraw(amount);
            System.out.println("Withdrawal successful! New balance: $" + account.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
        }
    }
    
    private static void deposit(BankAccount account) {
        System.out.print("\nEnter amount to deposit: $");
        try {
            double amount = Double.parseDouble(scanner.nextLine().trim());
            if (amount <= 0) {
                System.out.println("Invalid amount. Must be greater than 0.");
                return;
            }
            account.deposit(amount);
            System.out.println("Deposit successful! New balance: $" + account.getBalance());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
        }
    }
    
    private static void viewTransactionHistory(BankAccount account) {
        System.out.println("\n--- Transaction History ---");
        account.printTransactionHistory();
    }
}

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private StringBuilder transactionHistory;
    
    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.transactionHistory = new StringBuilder();
        this.transactionHistory.append("Account created with initial balance: $").append(initialBalance).append("\n");
    }
    
    public void withdraw(double amount) {
        balance -= amount;
        transactionHistory.append("Withdrawal: -$").append(amount).append(" | Balance: $").append(balance).append("\n");
    }
    
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append("Deposit: +$").append(amount).append(" | Balance: $").append(balance).append("\n");
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
    
    public void printTransactionHistory() {
        System.out.println(transactionHistory.toString());
    }
}