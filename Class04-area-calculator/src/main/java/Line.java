public class Line implements Shape {
    private double len;

    public Line(double l) {
        this.len = l;
    }


    public double getArea() throws NoAreaException {
        throw new NoAreaException();
    }

    @Override
    public void draw() {
        System.out.println("Line");
    }
}
