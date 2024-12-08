import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[] asciiLogo = {
                "*****   ***   *   *   ***  ",
                "  *      *    *  *   *   * ",
                "  *      *    ***    *   * ",
                "  *      *    *  *   *   * ",
                "  *     ***   *   *   ***  "
        };

        for (String line : asciiLogo) {
            System.out.println(line);
        }

        Configuration config = new Configuration();

        TicketPool ticketPool = new TicketPool(config.getMaxPoolCapacity());

        // Preload the ticket pool


        boolean systemActive = true;
        boolean ticketingActive = false;

        Thread[] vendorThreads = new Thread[config.getNumVendors()];
        Thread[] customerThreads = new Thread[config.getNumCustomers()];

        while (systemActive) {
            if (!ticketingActive) {
                System.out.println("\nMenu:");
                System.out.println("1. Start ticketing");
                System.out.println("2. Stop ticketing");
                System.out.println("3. View ticket pool status");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
            }
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    if (!ticketingActive) {
                        System.out.println("\nStarting ticketing...");
                        ticketingActive = true;

                        // Start vendor threads
                        for (int i = 0; i < config.getNumVendors(); i++) {
                            int vendorId = i + 1;
                            vendorThreads[i] = new Thread(new Vendor(ticketPool, config.getVendorIntervalMs(), vendorId));
                            vendorThreads[i].start();
                        }

                        // Start customer threads
                        for (int i = 0; i < config.getNumCustomers(); i++) {
                            int customerId = i + 1;
                            customerThreads[i] = new Thread(new Customer(ticketPool, config.getCustomerIntervalMs(), customerId));
                            customerThreads[i].start();
                        }
                    } else {
                        System.out.println("Ticketing system is already running!");
                    }
                    break;

                case 2:
                    if (ticketingActive) {
                        System.out.println("\nStopping ticketing...");
                        ticketingActive = false;

                        // Interrupt all threads
                        for (Thread vendor : vendorThreads) {
                            if (vendor != null) vendor.interrupt();
                        }
                        for (Thread customer : customerThreads) {
                            if (customer != null) customer.interrupt();
                        }
                    } else {
                        System.out.println("The system is not currently running.");
                    }
                    break;

                case 3:
                    System.out.println("\nCurrent ticket count: " + ticketPool.getTicketCount());
                    break;

                case 4:
                    System.out.println("\nExiting...");
                    systemActive = false;

                    // Ensure threads are stopped
                    for (Thread vendor : vendorThreads) {
                        if (vendor != null) vendor.interrupt();
                    }
                    for (Thread customer : customerThreads) {
                        if (customer != null) customer.interrupt();
                    }
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}