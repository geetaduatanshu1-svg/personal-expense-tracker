import java.time.LocalDate;

public class Expense {
    private int id;
    private double amount;
    private String category;
    private LocalDate date;
    private String description;

    public Expense(int id, double amount, String category, LocalDate date, String description) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
        this.description = description;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public LocalDate getDate() { return date; }
    public String getDescription() { return description; }

    // Convert expense object to CSV row format
    public String toCSV() {
        return id + "," + amount + "," + category + "," + date + "," + description.replace(",", " ");
    }

    // Parse CSV row back into an Expense object
    public static Expense fromCSV(String csvLine) {
        String[] parts = csvLine.split(",", 5);
        if (parts.length < 5) return null;
        try {
            int id = Integer.parseInt(parts[0].trim());
            double amount = Double.parseDouble(parts[1].trim());
            String category = parts[2].trim();
            LocalDate date = LocalDate.parse(parts[3].trim());
            String description = parts[4].trim();
            return new Expense(id, amount, category, date, description);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return String.format("[%d] Date: %s | Category: %-10s | Amount: $%.2f | Note: %s", 
                id, date, category, amount, description);
    }
}
