package examples.schema;

import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

public class Person {
    private String name;
    private int age;
    private boolean gender; // female is true, male is false

    private Person mother;
    private Person father;

    public Person(String name, int age, boolean gender, Person mother, Person father) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.mother = mother;
        this.father = father;
    }

    public String toString() {
        return String.format("{name=%s, age=%d, gender=%s}",
                name, age, gender ? "female" : "male");
    }

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgUpdate("name")
    public void setName(String name) {
        this.name = name;
    }

    @HgValue("age")
    public int getAge() {
        return age;
    }

    @HgValue("mother")
    public Person getMother() {
        return mother;
    }

    @HgValue("father")
    public Person getFather() {
        return father;
    }

    @HgValue("gender")
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

    @HgValue("canDrink")
    public boolean canDrink() {
        return age >= 21;
    }

    @HgValue("canSmoke")
    public boolean canSmoke() {
        return age >= 18;
    }

    @HgUpdate({"age", "canDrink", "canSmoke"})
    public void setAge(int age) {
        this.age = age;
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
