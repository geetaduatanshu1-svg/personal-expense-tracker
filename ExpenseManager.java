import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ExpenseManager {
    private List<Expense> expenses;
    private double monthlyBudget;
    private final String FILE_PATH = "expenses.csv";
    private int nextId;

    public ExpenseManager() {
        this.expenses = new ArrayList<>();
        this.monthlyBudget = 0.0;
        this.nextId = 1;
        loadFromFile();
    }

    public void addExpense(double amount, String category, String description) {
        Expense expense = new Expense(nextId++, amount, category, LocalDate.now(), description);
        expenses.add(expense);
        saveToFile();
        System.out.println("Expense added successfully!");
    }

    public void listAllExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded yet.");
            return;
        }
        System.out.println("\n--- All Expenses ---");
        expenses.forEach(System.out::println);
    }

    public void showCategorySummary() {
        if (expenses.isEmpty()) {
            System.out.println("No data available for summary.");
            return;
        }
        Map<String, Double> summary = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));

        System.out.println("\n--- Spending Summary by Category ---");
        summary.forEach((cat, total) -> System.out.printf("%-12s: $%.2f%n", cat, total));
    }

    public void setMonthlyBudget(double budget) {
        this.monthlyBudget = budget;
        saveToFile();
        System.out.printf("Monthly budget set to $%.2f%n", budget);
    }

    public void checkBudgetStatus() {
        double totalSpent = expenses.stream().mapToDouble(Expense::getAmount).sum();
        System.out.printf("\nTotal Spent: $%.2f", totalSpent);
        if (monthlyBudget > 0) {
            System.out.printf(" / Budget: $%.2f%n", monthlyBudget);
            if (totalSpent > monthlyBudget) {
                System.out.println("⚠️ Warning: You have exceeded your monthly budget!");
            } else {
                System.out.printf("You have $%.2f remaining.%n", (monthlyBudget - totalSpent));
            }
        } else {
            System.out.println("\n(No monthly budget has been set yet.)");
        }
    }

    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            writer.println(monthlyBudget); // Save budget on the first line
            for (Expense e : expenses) {
                writer.println(e.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String firstLine = reader.readLine();
            if (firstLine != null) {
                try {
                    monthlyBudget = Double.parseDouble(firstLine.trim());
                } catch (NumberFormatException e) {
                    Expense exp = Expense.fromCSV(firstLine);
                    if (exp != null) {
                        expenses.add(exp);
                        nextId = Math.max(nextId, exp.getId() + 1);
                    }
                }
            }

            String line;
            while ((line = reader.readLine()) != null) {
                Expense exp = Expense.fromCSV(line);
                if (exp != null) {
                    expenses.add(exp);
                    nextId = Math.max(nextId, exp.getId() + 1);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }
}
