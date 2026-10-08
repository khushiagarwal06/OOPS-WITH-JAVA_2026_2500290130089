public class UseCase5 {
    class TicketCounter implements Runnable {
    int availableTickets = 3;

    @Override
    public void run() {
        while (availableTickets > 0) {
            bookTickets();
        }
    }

    synchronized void bookTickets() {
        if (availableTickets > 0) {
            availableTickets = availableTickets - 1;
            
            System.out.println("Ticket booked by " + Thread.currentThread().getName());
            System.out.println("Left tickets are " + availableTickets);
        } else {
            System.out.println("Tickets are sold out");
        }
    }
    }
}
