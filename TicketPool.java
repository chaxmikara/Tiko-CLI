import java.util.ArrayList;

public class TicketPool {
    private final ArrayList<String> tickets;
    private final int capacity;
    private int ticketCounter;

    public TicketPool(int capacity) {
        this.tickets = new ArrayList<>();
        this.capacity = capacity;
        this.ticketCounter = 1;
    }

    public synchronized void addTickets(int count, int vendorId) {
        for (int i = 0; i < count; i++) {
            if (tickets.size() < capacity) {
                String ticket = "Ticket-" + ticketCounter++;
                tickets.add(ticket);
                System.out.println("Vendor " + vendorId + " added " + ticket);
            } else {
                System.out.println("Vendor " + vendorId + ": Ticket pool is full!");
                break;
            }
        }
    }

    public synchronized String retrieveTicket(int customerId) {
        if (!tickets.isEmpty()) {
            String ticket = tickets.remove(0);
            System.out.println("Customer " + customerId + " bought " + ticket);
            return ticket;
        } else {
            System.out.println("Customer " + customerId + ": No tickets available!");
            return null;
        }
    }

    public synchronized int getTicketCount() {
        return tickets.size();
    }
}