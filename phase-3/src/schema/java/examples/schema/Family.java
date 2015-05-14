package examples.schema;

import com.github.mercurydb.annotations.HgValue;

import java.util.Arrays;
import java.util.List;

public class Family {
    private Person mother;
    private Person father;
    private List<Person> children;

    public Family(Person mother, Person father, Person... children) {
        this.mother = mother;
        this.father = father;
        this.children = Arrays.asList(children);
    }

    @HgValue("mother")
    public Person getMother() {
        return mother;
    }

    @HgValue("father")
    public Person getFather() {
        return father;
    }

    @HgValue("children")
    public List<Person> getChildren() {
        return children;
    }

    @HgValue("firstChild")
    public Person getOldestChild() {
        int maxAge = 0;
        Person result = null;
        for (Person p : children) {
            if (p.getAge() > maxAge) {
                maxAge = p.getAge();
                result = p;
            }
        }
        return result;
    }
}
