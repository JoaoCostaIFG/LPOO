public class Attendee extends Person {
    private Boolean has_paid = false;

    public Attendee(String name) {
        super.setName(name);
    }

    public Attendee(String name, Integer age) {
        super.setName(name);
        super.setAge(age);
    }

    public boolean hasPaid() {
        return this.has_paid;
    }

    @Override
    public String toString() {
        return "Attendee " + super.getName() + (this.has_paid ? " has" : " hasn't") + " paid its registration.";
    }
}
