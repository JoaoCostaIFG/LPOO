import java.util.ArrayList;
import java.util.List;

public class Party extends Event {
    private List<Event> events = new ArrayList<>();

    public Party(String title, String date, String description) {
        super(title, date, description);
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    @Override
    public int getAudienceCount() {
        int cnt = super.getAudienceCount();
        for (Event e : this.events)
            cnt += e.getAudienceCount();
        return cnt;
    }
}
