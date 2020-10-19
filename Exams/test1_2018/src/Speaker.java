public class Speaker extends Person {
    private int fee = 0;

    public Speaker(String name) {
        super.setName(name);
    }

    public Speaker(String name, Integer age) {
        super.setName(name);
        super.setAge(age);
    }

    @Override
    public int getAge() {
        return this.age;
    }

    public int getFee() {
        return this.fee;
    }

    @Override
    public String toString() {
        return "Speaker " + super.getName() + " has a fee value of " + this.fee + ".";
    }
}
