public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int intervalMs;
    private final int customerId;

    public Customer(TicketPool ticketPool, int intervalMs, int customerId) {
        this.ticketPool = ticketPool;
        this.intervalMs = intervalMs;
        this.customerId = customerId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.retrieveTicket(customerId);
                Thread.sleep(intervalMs);
            }
        } catch (InterruptedException e) {
            System.out.println("Customer " + customerId + " stopped.");
        }
    }
}