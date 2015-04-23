package examples.schema;

import org.mercurydb.annotations.HgValue;

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
        return String.format("{name=%s, age=%d, sex=%b}", name, age, sex);
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
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER

    }



}
