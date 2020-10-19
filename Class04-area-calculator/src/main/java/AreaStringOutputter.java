public class AreaStringOutputter {
    private SumProvider aa;

    public AreaStringOutputter(SumProvider aa) {
        this.aa = aa;
    }

    public String output() {
        return "Sum of areas: " + aa.sum();
    }
}
