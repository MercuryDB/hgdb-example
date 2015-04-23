package examples.schema;

public class Person {
    private String name;
    private int age;
    private boolean gender; // female is true, male is false

    public Person(String name, int age, boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String toString() {
        return String.format("{name=%s, age=%d, gender=%s}",
                name, age, gender ? "female" : "male");
    }
}
