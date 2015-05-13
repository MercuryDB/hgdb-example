package examples.schema;

import com.github.mercurydb.annotations.HgUpdate;
import com.github.mercurydb.annotations.HgValue;

/**
 * Created by colestewart on 5/9/15.
 */
public class Department {
    private String name;
    private Employee manager;

    public Department(String name) {
        this.name = name;
    }

    @HgValue("name")
    public String getName() {
        return name;
    }

    @HgValue("manager")
    public Employee getManager() {
        return manager;
    }

    @HgUpdate({"manager"})
    public void setManager(Employee manager) {
        this.manager = manager;
    }
}
