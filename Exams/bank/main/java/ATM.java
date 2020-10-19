import java.util.Objects;

public class ATM {
    private int id;
    private String location, name;

    public ATM(int id, String location, String name) {
        this.id = id;
        this.location = location;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ATM " + id + " (" + location + ", " + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATM atm = (ATM) o;
        return id == atm.id &&
                Objects.equals(location, atm.location) &&
                Objects.equals(name, atm.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, name);
    }
}
