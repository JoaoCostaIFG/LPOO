public abstract class Transaction {
    protected double amount;
    protected ATM atm;
    protected Session s;
    protected Card c;

    public Transaction(ATM atm, Session s, Card c, double amount) {
        this.atm = atm;
        this.s = s;
        this.c = c;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
