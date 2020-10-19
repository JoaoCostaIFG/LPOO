import java.util.LinkedList;
import java.util.List;

public class City implements SumProvider {
    private List<House> houses;

    public City() {
        this.houses = new LinkedList<>();
    }

    public City(List<House> hs) {
        this.houses = hs;
    }

    public void addHouse(House h) {
        this.houses.add(h);
    }

    public double sum() {
        double sum = 0;
        for (House h : houses) {
            sum += h.getArea();
        }
        return sum;
    }
}
