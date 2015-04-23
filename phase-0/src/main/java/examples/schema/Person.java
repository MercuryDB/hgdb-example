package examples.schema;

public class Person {
    private String name;
    private int age;
    private boolean sex; // male is true, female is false

    public Person(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String toString() {
        return String.format("{name=%s, age=%d, sex=%s}",
                name, age, sex ? "male" : "female");
    }
}
