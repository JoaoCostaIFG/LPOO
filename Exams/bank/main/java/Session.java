import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session implements Countable {
    private ATM atm;
    private List<Transaction> transactions;

    public Session(ATM atm) {
        this.atm = atm;
        this.transactions = new ArrayList<>();
    }

    public Session(Session s) {
        this.atm = s.getATM();
        this.transactions = s.getTransactions();
    }

    public ATM getATM() {
        return atm;
    }

    public void setATM(ATM atm) {
        this.atm = atm;
    }

    public void addTransaction(Transaction t) {
        this.transactions.add(t);
    }

    public List<Transaction> getTransactions() {
        return this.transactions;
    }

    @Override
    public int count() {
        return this.transactions.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(atm, session.atm) &&
                Objects.equals(transactions, session.transactions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atm, transactions);
    }
}
