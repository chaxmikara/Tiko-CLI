public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int intervalMs;
    private final int vendorId;

    public Vendor(TicketPool ticketPool, int intervalMs, int vendorId) {
        this.ticketPool = ticketPool;
        this.intervalMs = intervalMs;
        this.vendorId = vendorId;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                ticketPool.addTickets(1, vendorId);
                Thread.sleep(intervalMs);
            }
        } catch (InterruptedException e) {
            System.out.println("Vendor " + vendorId + " stopped.");
        }
    }
}