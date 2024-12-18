import java.util.Scanner;

public class Wallet {
    private double balance;
    private User user = new User();
    private Scanner input = new Scanner(System.in);

    public Wallet() {
        this.balance = 0.0;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        user.clearScreen();
        if (amount > 0) {
            balance += amount;
            System.out.printf("Successfully deposited $ %.2f", amount);
            System.out.printf("New balance: $ %.2f%n", balance);
            System.out.println("------------------------------------------");
        }
        else {
            System.out.println("---Invalid deposit amount.");
        }
        System.out.println();
        System.out.print(" > Press 'enter' to return to wallet.");
        input.nextLine();
    }

    public void withdraw(double amount) {
        System.out.println();
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.printf("Successfully withdrew $ %.2f", amount);
                System.out.printf("Remaining balance: $ %.2f%n", balance);
                System.out.println("------------------------------------------");
            }
            else {
                System.out.println("---Insufficient funds in the wallet.");
            }
        }
        else {
            System.out.println("---Invalid withdrawal amount.");
        }
        System.out.println();
        System.out.print(" > Press 'enter' to return to wallet.");
    }
}