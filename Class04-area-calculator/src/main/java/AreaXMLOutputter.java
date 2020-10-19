public class AreaXMLOutputter {
    private SumProvider aa;

    public AreaXMLOutputter(SumProvider aa) {
        this.aa = aa;
    }

    public String output() {
        return "<area>" + aa.sum() + "</area>";
    }
}
