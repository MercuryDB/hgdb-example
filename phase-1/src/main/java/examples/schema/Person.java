package examples.schema;

import org.mercurydb.annotations.HgValue;
import org.mercurydb.annotations.HgUpdate;
import org.mercurydb.annotations.HgIndexStyle;

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

    @HgValue(value = "name", index = HgIndexStyle.ORDERED)
    public String getName() {
        return name;
    }

    @HgUpdate("name")
    public void setName(String name) {
        this.name = name;
    }

    @HgValue(value = "age", index = HgIndexStyle.ORDERED)
    public int getAge() {
        return age;
    }

    @HgUpdate("age")
    public void setAge(int age) {
        this.age = age;
    }

    @HgValue(value = "gender", index = HgIndexStyle.UNORDERED)
    public Gender getGender() {
        if (gender) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    @HgUpdate("gender")
    public void setGender(Gender gender) {
        switch (gender) {
            case MALE:
                this.gender = false;
                break;
            case FEMALE:
                this.gender = true;
                break;
            default:
                break;
        }
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
