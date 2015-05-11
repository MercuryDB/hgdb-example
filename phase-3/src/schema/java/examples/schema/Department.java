package examples.schema;

import com.github.mercurydb.annotations.HgValue;

/**
 * Created by colestewart on 5/9/15.
 */
public class Department {
    private String name;
    private Employee manager;

    public Department(String name, Employee manager) {
        this.name = name;
        this.manager = manager;
    }

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgValue("manager")
    public Employee getManager() {
        return manager;
    }
}
