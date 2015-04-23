package examples.schema;

import org.mercurydb.annotations.HgValue;

public class Person {
    private String name;
    private int age;
    private boolean sex; // female is true, male is false

    public Person(String name, int age, boolean sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String toString() {
        return String.format("{name=%s, age=%d, sex=%s}",
                name, age, sex ? "female" : "male");
    }

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgValue("age")
    public int getAge() {
        return age;
    }

    @HgValue("gender")
    public Gender getSex() {
        if (sex) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
