import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room implements Facility {
    private Building building;
    private String number;
    private int floor, capacity = 0;
    private List<User> users = new ArrayList<>();

    public Room(Building b, String number, int floor) throws DuplicateRoomException {
        this.building = b;
        this.number = number;
        this.floor = floor;

        if (!building.addRoom(this))
            throw new DuplicateRoomException();

        if (floor > b.getMaxFloor() || floor < b.getMinFloor())
            throw new IllegalArgumentException();
    }

    public Room(Building b, String number, int floor, int capacity) throws IllegalArgumentException, DuplicateRoomException {
        this(b, number, floor);
        this.capacity = capacity;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getName() {
        return building.getName() + number;
    }

    @Override
    public String toString() {
        return "Room(" + building.getName() + "," + number + ")";
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return floor == room.floor &&
                capacity == room.capacity &&
                Objects.equals(building, room.building) &&
                Objects.equals(number, room.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(building, number, floor, capacity);
    }

    public void authorize(User u) {
        this.users.add(u);
    }

    @Override
    public boolean canEnter(User u) {
        return users.contains(u);
    }
}
