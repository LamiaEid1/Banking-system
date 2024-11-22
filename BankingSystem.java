
import java.io.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private String accountNumber;
    private String accountHolderName;
    private double balance;

    public Account(String accountNumber, String accountHolderName, double initialDeposit) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
        	balance = balance + amount;
            System.out.println("Successfully deposited $" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Successfully withdrew $" + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Account Holder: " + accountHolderName + ", Balance: $" + balance;
    }
}

class Bank {
    private Map<String, Account> accounts = new HashMap<>();
    private final String DATA_FILE = "accounts.dat";

    public Bank() {
        loadAccounts();
    }

    public void createAccount(String accountNumber, String accountHolderName, double initialDeposit) {
        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account number already exists.");
        } else {
            Account newAccount = new Account(accountNumber, accountHolderName, initialDeposit);
            accounts.put(accountNumber, newAccount);
            System.out.println("Account created successfully!");
        }
    }

    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.withdraw(amount);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void viewAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            System.out.println(account);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(accounts);
            System.out.println("Account data saved.");
        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
	private void loadAccounts() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                accounts = (Map<String, Account>) ois.readObject();
                System.out.println("Account data loaded.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Error loading account data: " + e.getMessage());
            }
        } else {
            System.out.println("No existing account data found. Starting fresh.");
        }
    }
}

public class BankingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Bank bank = new Bank();

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    deposit();
                    break;
                case 3:
                    withdraw();
                    break;
                case 4:
                    viewAccount();
                    break;
                case 5:
                    bank.saveAccounts();
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\n--- Banking System Menu ---");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. View Account Details");
        System.out.println("5. Save and Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter initial deposit amount: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        bank.createAccount(accountNumber, accountHolderName, initialDeposit);
    }

    private static void deposit() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter deposit amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        bank.deposit(accountNumber, amount);
    }

    private static void withdraw() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter withdrawal amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        bank.withdraw(accountNumber, amount);
    }

    private static void viewAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        bank.viewAccount(accountNumber);
    }
}