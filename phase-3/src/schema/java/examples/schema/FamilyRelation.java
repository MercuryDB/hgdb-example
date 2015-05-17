package examples.schema;

import com.github.mercurydb.annotations.HgValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FamilyRelation {
    private Person mother;
    private Person father;
    private List<Person> children;

    public FamilyRelation(Person mother, Person father, Person... children) {
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

    public String toString() {
        return "(Family | " + mother.getName() + " + " + father.getName() + ") = " +
                children.stream().map(p -> p.getName())
                .collect(Collectors.joining(", "));
    }
}
