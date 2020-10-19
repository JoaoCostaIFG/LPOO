import java.util.ArrayList;
import java.util.List;

public class BoxOffice {
    public static List<Ticket> buy(Concert concert, int num) throws InvalidTicket {
        List<Ticket> ret = new ArrayList<>();
        int n = concert.getLastTicketNum();

        for (int i = 0; i < num; ++i)
            ret.add(new Ticket(++n, concert));
        concert.setLastTicketNum(n);

        return ret;
    }
}
