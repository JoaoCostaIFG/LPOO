public class House implements HasArea {
    private double side;

    public House(double s) {
        this.side = s;
    }

    @Override
    public double getArea() {
        return Math.pow(this.side, 2);
    }
}
