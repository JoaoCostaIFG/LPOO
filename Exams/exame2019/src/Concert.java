import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Concert {
    private List<Act> acts;
    private String city, country, date;
    private int lastTicketNum = 0;

    public Concert(String city, String country, String date) {
        this.city = city;
        this.country = country;
        this.date = date;
        this.acts = new ArrayList<>();
    }

    public void addAct(Act a) {
        this.acts.add(a);
    }

    public List<Act> getActs() {
        return this.acts;
    }

    public String getCity() {
        return this.city;
    }

    public String getCountry() {
        return this.country;
    }

    public String getDate() {
        return this.date;
    }

    public boolean isValid(Ticket t) {
        return this.equals(t.getConcert());
    }

    public boolean participates(Artist toFind) {
        for (Act a : acts) {
            if (a instanceof Band) {
                if (((Band) a).containsArtist(toFind))
                    return true;
            } else if (a.equals(toFind))
                return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Concert concert = (Concert) o;
        return Objects.equals(acts, concert.acts) &&
                Objects.equals(city, concert.city) &&
                Objects.equals(country, concert.country) &&
                Objects.equals(date, concert.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acts, city, country, date);
    }

    public int getLastTicketNum() {
        return lastTicketNum;
    }

    public void setLastTicketNum(int lastTicketNum) {
        this.lastTicketNum = lastTicketNum;
    }
}
