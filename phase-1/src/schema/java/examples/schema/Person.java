package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

@SuppressWarnings("unused")
public class Person {
    // declare private state first, public fields discouraged

    private String name;
    private int age;
    private Gender gender;

    // declare all constructors

    public Person(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    // declare all @HgValues after constructors so they are easy to find

    @HgValue(value = "name", index = HgIndexStyle.ORDERED)
    public String getName() {
        return name;
    }

    @HgValue(value = "age", index = HgIndexStyle.ORDERED)
    public int getAge() {
        return age;
    }

    @HgValue(value = "canDrink", index = HgIndexStyle.UNORDERED)
    public boolean canDrink() {
        return getAge() >= 21;
    }

    @HgValue(value = "canSmoke", index = HgIndexStyle.UNORDERED)
    public boolean canSmoke() {
        return getAge() >= 18;
    }

    @HgValue(value = "gender", index = HgIndexStyle.UNORDERED)
    public Gender getGender() {
        return gender;
    }

    // declare all @HgUpdates after @HgValues so they are grouped together

    @HgUpdate("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Update the age of this Person. Multiple @HgValues depend on
     * the value of age (canSmoke and canDrink), so annotate that this
     * method updates all of them.
     *
     * @param age The new age.
     */
    @HgUpdate({"age", "canSmoke", "canDrink"})
    public void setAge(int age) {
        this.age = age;
    }

    @HgUpdate("gender")
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    // declare any other helper code or methods that are not @HgValues or @HgUpdates

    public String toString() {
        return String.format("{name=%s, age=%d, gender=%s}",
                name, age, gender);
    }

    @SuppressWarnings("unused")
    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
