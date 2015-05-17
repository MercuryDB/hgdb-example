package examples.app;

import com.github.mercurydb.queryutils.HgDB;
import com.github.mercurydb.queryutils.HgStream;
import com.github.mercurydb.queryutils.TableID;
import examples.db.PersonTable;
import examples.schema.Person;

public class Main {
    public static final TableID<Person> PERSON_ALIAS = PersonTable.createAlias();

    public static void main(String[] args) {
        // Our nice little database. The constructor bytecode hooks will insert the
        // people into the database!

        Person[] people = {
                new Person("Huck Finn", 17, Person.Gender.MALE),
                new Person("Jane Eyre", 19, Person.Gender.FEMALE),
                new Person("Tyler Durden", 24, Person.Gender.MALE),
                new Person("Al Adin", 23, Person.Gender.MALE),
        };

        System.out.println("\nThe Database:");
        PersonTable.stream().forEach(System.out::println);

        // ===========================
        // Single Table Filter Queries
        // ===========================

        // Query all people with ages >= 21
        System.out.println("\nages >= 21:");
        HgStream<Person> stream = HgDB.query(PersonTable.ge.age(21));
        stream.forEach(System.out::println);

        // Query all people with first names that begin with a letter after I
        System.out.println("\nname >= 'I':");
        HgDB.query(PersonTable.ge.name("I")).forEach(System.out::println);

        // Query all people that are male and less than 24
        System.out.println("\nmale and age < 24:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.lt.age(24))
                .forEach(System.out::println);

        // To show the difference between LT and LE:
        // Query all people that are male and less than or equal to 24
        System.out.println("\nmale and age <= 24:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.le.age(24))
                .forEach(System.out::println);

        // Query a range of values
        System.out.println("\nage between 18 and 21 inclusive");
        HgDB.query(
                PersonTable.ge.age(18),
                PersonTable.le.age(21))
                .forEach(System.out::println);

        // Query a disjunction of predicates
        System.out.println("\nage < 18 or >= 21");
        HgDB.query(PersonTable.lt.age(18))
                .concat(HgDB.query(PersonTable.gt.age(21)))
                .forEach(System.out::println);

        // Query all people whose age is divisible by 2
        System.out.println("\nage % 2 == 0:");
        HgDB.query(PersonTable.predicate(p -> p.getAge() % 2 == 0))
                .forEach(System.out::println);

        // Great! Everybody had a birthday recently!
        for (Person p : people) {
            p.setAge(p.getAge() + 1);
        }

        // Query all people that are male and less than 24
        System.out.println("\nmale and age < 24:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.lt.age(24))
                .forEach(System.out::println);
    }
}
