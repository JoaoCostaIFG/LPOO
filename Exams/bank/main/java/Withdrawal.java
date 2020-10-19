public class Withdrawal extends Transaction {
    public Withdrawal(ATM atm, Session s, Card c, double amount) {
        super(atm, s, c, amount);
    }

    @Override
    public String toString() {
        return "Withdrawal at " + atm.toString() + " of " + String.format("%.2f", amount).replace(",", ".");
    }
}
