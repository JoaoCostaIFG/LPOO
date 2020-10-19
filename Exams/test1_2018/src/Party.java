import java.util.LinkedList;
import java.util.List;

public class Party extends Event {
    String location = "", date = "", msg = "";
    List<Person> people = new LinkedList<>();
    List<Event> events = new LinkedList<>();

    public Party(String location, String date, String msg) {
        super(location);
        this.location = location;
        this.date = date;
        this.msg = msg;
    }

    public void addPerson(Person p) {
        this.people.add(p);
    }

    public int getAudienceCount() {
        int ret = people.size();
        for (Event e : this.events) {
            ret += e.getAudienceCount();
        }
        return ret;
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }
}
