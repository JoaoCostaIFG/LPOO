public class Rectangulo implements Figura {
    private Ponto c1, c2;

    public Rectangulo(int x1, int y1, int x2, int y2) {
        this.c1 = new Ponto(x1, y1);
        this.c2 = new Ponto(x2, y2);
    }

    @Override
    public double getArea() {
        return (c2.getX() - c1.getX()) * (c2.getY() - c1.getY());
    }

    @Override
    public double getPerimetro() {
        return 2 * (c2.getX() - c1.getX()) + 2 * (c2.getY() - c1.getY());
    }
}
