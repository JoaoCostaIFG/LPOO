import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.TreeSet;

public class Event {
    private String title, date, description;
    private TreeSet<Person> audience = new TreeSet<>();

    public Event(String title, String date, String description) {
        this.title = title;
        this.date = date;
        this.description = description;
    }

    public Event(String title, String date) {
        this(title, date, "");
    }

    public Event(String title) {
        this(title, "", "");
    }

    public Event(Event e) {
        this(e.getTitle(), e.getDate(), e.getDescription());
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return title + " is a " + description + " and will be held at " + date + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(title, event.title) &&
                Objects.equals(date, event.date) &&
                Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, description);
    }

    public void addPerson(Person p) {
        this.audience.add(p);
    }

    public int getAudienceCount() {
        return this.audience.size();
    }
}
