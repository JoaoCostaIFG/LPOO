public class Triangle implements AreaShape {
    private double base, height;

    public Triangle(int b, int h) {
        this.base = b;
        this.height = h;
    }

    @Override
    public void draw() {
        System.out.println("Triangle");
    }

    @Override
    public double getArea() {
        return base * height / 2;
    }
}
