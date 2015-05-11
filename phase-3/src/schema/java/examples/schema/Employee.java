package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

/**
 * Created by colestewart on 5/9/15.
 */
public class Employee extends Person {
    private Department department;
    private String email;
    private String location;
    private double salary;

    public Employee(
                String name, int age, boolean gender, Person mother, Person father,
                Department department, String email, String location, double salary) {
        super(name, age, gender, mother, father);
        this.department = department;
        this.email = email;
        this.location = location;
        this.salary = salary;
    }

    @HgValue("department")
    public Department getDepartment() {
        return department;
    }

    @HgValue("email")
    public String getEmail() {
        return email;
    }

    @HgValue("location")
    public String getLocation() {
        return location;
    }

    @HgValue(value="salary", index=HgIndexStyle.ORDERED)
    public double getSalary() {
        return salary;
    }

    @HgUpdate({"salary"})
    public void giveTenPercentRaise() {
        salary += salary * 0.1;
    }
}
