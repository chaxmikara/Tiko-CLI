//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Configuration {
    private final int initialTicketCount;
    private final int vendorIntervalMs;
    private final int customerIntervalMs;
    private final int maxPoolCapacity;
    private final int numVendors;
    private final int numCustomers;

    public Configuration() {
        Scanner scanner = new Scanner(System.in);
        this.initialTicketCount = getPositiveInput(scanner, "Enter total tickets (initial capacity) to hold in pool: ");
        this.vendorIntervalMs = getPositiveInput(scanner, "Enter ticket release rate (s): ") * 1000;
        this.customerIntervalMs = getPositiveInput(scanner, "Enter customer retrieval rate (s): ") * 1000;
        this.maxPoolCapacity = getPositiveInput(scanner, "Enter max ticket capacity: ");
        this.numVendors = getPositiveInput(scanner, "Enter number of vendors: ");
        this.numCustomers = getPositiveInput(scanner, "Enter number of customers: ");
        writeToJsonFile("config.txt");
    }

    private int getPositiveInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value > 0) {
                    return value;
                } else {
                    System.out.println("Please enter a number greater than zero.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    private void writeToJsonFile(String fileName) {
        String filePath = "E:\\my_projects\\Tiko-CLI\\src\\" + fileName; // specify your desired path here // specify your desired path here
        try (FileWriter file = new FileWriter(filePath)) {
            file.write("{\n");
            file.write("  \"initialTicketCount\": " + initialTicketCount + ",\n");
            file.write("  \"vendorIntervalMs\": " + vendorIntervalMs/1000 + ",\n");
            file.write("  \"customerIntervalMs\": " + customerIntervalMs/1000 + ",\n");
            file.write("  \"maxPoolCapacity\": " + maxPoolCapacity + ",\n");
            file.write("  \"numVendors\": " + numVendors + ",\n");
            file.write("  \"numCustomers\": " + numCustomers + "\n");
            file.write("}\n");
            System.out.println("Configuration saved to " + filePath);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public int getInitialTicketCount() {
        return initialTicketCount;
    }

    public int getVendorIntervalMs() {
        return vendorIntervalMs;
    }

    public int getCustomerIntervalMs() {
        return customerIntervalMs;
    }

    public int getMaxPoolCapacity() {
        return maxPoolCapacity;
    }

    public int getNumVendors() {
        return numVendors;
    }

    public int getNumCustomers() {
        return numCustomers;
    }
}