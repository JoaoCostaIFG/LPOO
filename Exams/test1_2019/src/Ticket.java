public class Ticket {
    private int id;
    private Concert concert;

    public Ticket(int id, Concert concert) throws InvalidTicket {
        if (id < 0)
            throw new InvalidTicket();
        this.id = id;
        this.concert = concert;
    }

    public int getNumber() {
        return id;
    }

    public Concert getConcert() {
        return concert;
    }
}
