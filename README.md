# Personal Expense Tracker (CLI)

A simple command-line personal expense tracker written in Java. It supports adding expenses, viewing spending summaries by category, setting a monthly budget, and persisting records locally via CSV.

## Project Structure
- `Expense.java`: Model class representing an individual expense record.
- `ExpenseManager.java`: Core logic for managing expenses, calculating summaries, and reading/writing `expenses.csv`.
- `Main.java`: Interactive CLI menu loop.

## How to Compile & Run

1. Open a terminal in the project directory.
2. Compile all `.java` files:
   ```bash
   javac Main.java Expense.java ExpenseManager.java
   ```
3. Run the application:
   ```bash
   java Main
   ```
# personal-expense-tracker
