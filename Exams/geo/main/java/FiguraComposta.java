public class FiguraComposta implements Countable, Figura {
    private Figura[] figuras;

    public FiguraComposta(Figura[] figuras) {
        this.figuras = figuras;
    }

    @Override
    public double getArea() {
        double ret = 0;
        for (Figura f : figuras)
            ret += f.getArea();
        return ret;
    }

    @Override
    public double getPerimetro() {
        double ret = 0;
        for (Figura f : figuras)
            ret += f.getPerimetro();
        return ret;
    }

    @Override
    public int count() {
        return figuras.length;
    }
}
