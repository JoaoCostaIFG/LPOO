import java.util.ArrayList;
import java.util.List;

public class Building implements Facility {
    private String name;
    private List<Room> rooms = new ArrayList<>();
    private int minFloor, maxFloor;

    public Building(String name, int minFloor, int maxFloor) {
        this.name = name;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;

        if (minFloor > maxFloor)
            throw new IllegalArgumentException();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean canEnter(User u) {
        for (Room r : rooms) {
            if (r.canEnter(u))
                return true;
        }

        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinFloor() {
        return minFloor;
    }

    public void setMinFloor(int minFloor) {
        this.minFloor = minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    @Override
    public String toString() {
        return "Building(" + name + ")";
    }

    public int getCapacity() {
        int capacity = 0;
        for (Room r : rooms)
            capacity += r.getCapacity();

        return capacity;
    }

    public boolean addRoom(Room newr) {
        for (Room r : rooms) {
            if (r.equals(newr))
                return false;
        }

        this.rooms.add(newr);
        return true;
    }
}
