import java.util.LinkedList;
import java.util.List;

public class BoxOffice {
    public static List<Ticket> buy(Concert concert, int n) throws InvalidTicket {
        List<Ticket> ret = new LinkedList<>();

        for (Integer j = 0; j < n; ++j){
            ret.add(new Ticket(concert.buyTicket(), concert));
        }

        return ret;
    }
}
