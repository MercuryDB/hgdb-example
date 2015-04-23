package examples.schema;

import org.mercurydb.annotations.HgValue;
import org.mercurydb.annotations.HgUpdate;

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

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgValue("age")
    public int getAge() {
        return age;
    }

    @HgValue("gender")
    public Gender getGender() {
        if (gender) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
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
