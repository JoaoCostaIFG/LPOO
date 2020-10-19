public class Artist extends Person {
    public Artist(String name) {
        super.setName(name);
    }

    public Artist(String name, int age) {
        super.setName(name);
        super.setAge(age);
    }
}
