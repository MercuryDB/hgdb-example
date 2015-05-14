package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Person {
    private String name;
    private Date birthday;
    private boolean gender; // female is true, male is false

    private Calendar calendar;

    public Person(Person person) {
        this(person.name, person.birthday, person.gender);
    }

    public Person(String name, String birthday, boolean gender) {
        this.name = name;
        try {
            this.birthday = new SimpleDateFormat("MM/dd/yyyy").parse(birthday);
        } catch (ParseException e) {
            System.err.println("Invalid date! Must follow format of MM/dd/yyyy");
        }
        this.gender = gender;

        this.calendar = Calendar.getInstance();
    }

    public Person(String name, Date birthday, boolean gender) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String toString() {
        return String.format("{name=%s, birthday=%s, gender=%s}",
                name, birthday, gender ? "female" : "male");
    }

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgUpdate("name")
    public void setName(String name) {
        this.name = name;
    }

    @HgValue(value="age", index= HgIndexStyle.ORDERED)
    public int getAge() {
        Date today = new Date();
        calendar.setTime(today);
        int yearNow = calendar.get(Calendar.YEAR);
        calendar.setTime(birthday);
        int yearThen = calendar.get(Calendar.YEAR);
        return yearNow - yearThen;
    }

    @HgValue("birthday")
    public Date getBirthday() {
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
