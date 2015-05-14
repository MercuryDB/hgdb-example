package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Person {
    private String name;
    private LocalDate birthday;
    private boolean gender; // female is true, male is false

    private LocalDate today; // now = construction time (because letting this change dynamically is an issue)

    private void initialize(String name, LocalDate birthday, boolean gender) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.today = LocalDate.now();
    }

    public Person(String name, LocalDate birthday, boolean gender) {
        initialize(name, birthday, gender);
    }

    public Person(Person person) {
        initialize(person.name, person.birthday, person.gender);
    }

    public Person(String name, String birthday, boolean gender) {
        LocalDate bday = LocalDate.parse(birthday, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        initialize(name, bday, gender);
    }

    public String toString() {
        return String.format("{name=%s, birthday=%s, gender=%s}",
                name, birthday, gender ? "female" : "male");
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
        long years = ChronoUnit.YEARS.between(birthday, today);
        return (int) years;
    }

    @HgValue(value = "birthday", index = HgIndexStyle.ORDERED)
    public LocalDate getBirthday() {
        return birthday;
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
        return getAge() >= 21;
    }

    @HgValue("canSmoke")
    public boolean canSmoke() {
        return getAge() >= 18;
    }

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
