public class UseCase5 {
    class TicketCounter {
        private int availableTickets;

        public TicketCounter(int availableTickets) {
            this.availableTickets = availableTickets;
        }

        public synchronized void bookTicket(int ticketsToBook) {
            if (ticketsToBook <= availableTickets) {
                System.out.println("Booking " + ticketsToBook + " tickets.");
                availableTickets -= ticketsToBook;
                System.out.println("Tickets booked successfully. Remaining tickets: " + availableTickets);
            } else {
                System.out.println("Not enough tickets available. Remaining tickets: " + availableTickets);
            }
        }
    }
}