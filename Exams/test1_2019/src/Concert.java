import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Concert {
    private String city, country, date;
    private List<Act> acts;
    private Integer id;

    public Concert(String city, String country, String date) {
        this.city = city;
        this.country = country;
        this.date = date;

        this.id = 0;
        this.acts = new LinkedList<>();
    }

    public Integer buyTicket() {
        ++this.id;
        return this.id;
    }

    public void addAct(Act act) {
        this.acts.add(act);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Concert concert = (Concert) o;
        return city.equals(concert.city) &&
                country.equals(concert.country) &&
                date.equals(concert.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, country, date);
    }

    public boolean isValid(Ticket ticket) {
        return (this.equals(ticket.getConcert()));
    }

    public boolean participates(Artist artist) {
        for (Act act : this.acts) {
            if (act instanceof Artist) {
                if (act.equals(artist))
                    return true;
            }
            else {
                Band b = (Band) act;
                for (Artist a : b.getArtists())
                    if (a.equals(artist))
                        return true;
            }
        }
        return false;
    }
}
