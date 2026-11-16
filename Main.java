import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ExpenseManager manager = new ExpenseManager();
        boolean running = true;

        System.out.println("=== Welcome to Personal Expense Tracker ===");

        while (running) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. View Category Summary");
            System.out.println("4. Set Monthly Budget");
            System.out.println("5. Check Budget Status");
            System.out.println("6. Exit");
            System.out.print("Enter choice (1-6): ");

            String choiceInput = scanner.nextLine();
            int choice;
            try {
                choice = Integer.parseInt(choiceInput.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter amount ($): ");
                    double amount;
                    try {
                        amount = Double.parseDouble(scanner.nextLine());
                        if (amount <= 0) {
                            System.out.println("Amount must be greater than zero.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount format.");
                        break;
                    }

                    System.out.print("Enter category (e.g., Food, Travel, Utilities): ");
                    String category = scanner.nextLine().trim();
                    if (category.isEmpty()) category = "General";

                    System.out.print("Enter description/note: ");
                    String description = scanner.nextLine().trim();

                    manager.addExpense(amount, category, description);
                    break;

                case 2:
                    manager.listAllExpenses();
                    break;

                case 3:
                    manager.showCategorySummary();
                    break;

                case 4:
                    System.out.print("Enter monthly budget amount ($): ");
                    try {
                        double budget = Double.parseDouble(scanner.nextLine());
                        manager.setMonthlyBudget(budget);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid budget format.");
                    }
                    break;

                case 5:
                    manager.checkBudgetStatus();
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting... Your data has been saved. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Please select between 1 and 6.");
            }
        }
        scanner.close();
    }
}
