public class Speaker extends Person {
    private int fee = 0;

    public Speaker(String name) {
        super(name);
    }

    public Speaker(String name, int age) {
        super(name, age);
    }

    public int getFee() {
        return this.fee;
    }

    @Override
    public String toString() {
        return "Speaker " + name + " has a fee value of " + fee + ".";
    }
}
