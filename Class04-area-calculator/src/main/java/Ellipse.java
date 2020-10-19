public class Ellipse implements AreaShape {
    private double x_rad, y_rad;

    public double getX_rad() {
        return x_rad;
    }

    public double getY_rad() {
        return y_rad;
    }

    public Ellipse(double x_rad, double y_rad) {
        this.x_rad = x_rad;
        this.y_rad = y_rad;
    }

    @Override
    public double getArea() {
        return Math.PI * x_rad * y_rad;
    }

    @Override
    public void draw() {
        System.out.println("Ellipse");
    }
}
