public class Package {
    private String id;
    private String name;
    private int lessonsCount;
    private double price;
    private String description;

    public Package(String id, String name, int lessonsCount, double price, String description) {
        this.id = id;
        this.name = name;
        this.lessonsCount = lessonsCount;
        this.price = price;
        this.description = description;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getLessonsCount() { return lessonsCount; }
    public void setLessonsCount(int lessonsCount) { this.lessonsCount = lessonsCount; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String toFileString() {
        return id + "|" + name + "|" + lessonsCount + "|" + price + "|" + description;
    }

    public static Package fromFileString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length != 5) {
            // Skip invalid lines (log error or return null)
            System.err.println("Skipping malformed line: " + line);
            return null;
        }
        return new Package(parts[0], parts[1], Integer.parseInt(parts[2]),
                Double.parseDouble(parts[3]), parts[4]);
    }
}