package examples.schema;

import com.github.mercurydb.annotations.HgIndexStyle;
import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

public class Employee extends Person {
    private Department department;
    private String email;
    private String location;
    private double salary;

    public Employee(
                Person person,
                Department department, String email, String location, double salary) {
        super(person);
        this.department = department;
        this.email = email;
        this.location = location;
        this.salary = salary;
    }

    @HgValue("department")
    public Department getPrimaryDepartment() {
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
