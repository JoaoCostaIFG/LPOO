public class Ticket {
    private int number;
    private Concert concert;

    public Ticket(int number, Concert concert) throws InvalidTicket {
        this.number = number;
        this.concert = concert;

        if (this.number <= 0)
            throw new InvalidTicket();
    }

    public int getNumber() {
        return this.number;
    }

    public Concert getConcert() {
        return this.concert;
    }
}
