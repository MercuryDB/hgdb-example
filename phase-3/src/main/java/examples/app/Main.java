package examples.app;

import com.github.mercurydb.queryutils.HgDB;
import com.github.mercurydb.queryutils.HgRelation;
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
                new Person("John Doe", 17, false),
                new Person("Rhianne Styles", 19, true),
                new Person("Cole Stewart", 23, false),
                new Person("Doug Ilijev", 24, false),
        };

        System.out.println("The Database:");
        PersonTable.stream().forEach(System.out::println);

        // ===========================
        // Single Table Filter Queries
        // ===========================

        //  Query all people with ages >= 21
        System.out.println("\nages >= 1:");
        HgStream<Person> stream = HgDB.query(PersonTable.ge.age(21));
        stream.forEachRemaining(System.out::println);

        //  Query all people with first names that begin with a letter after I
        System.out.println("\nname >= 'I':");
        HgDB.query(PersonTable.ge.name("I")).forEachRemaining(System.out::println);

        // Query all people that are male and less than 23
        System.out.println("\nmale and age < 23:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.lt.age(23))
                .forEachRemaining(System.out::println);

        // Query all people whose age is divisible by 2
        System.out.println("\nage % 2 == 0:");
        HgDB.query(PersonTable.predicate(p -> p.getAge() % 2 == 0))
                .forEachRemaining(System.out::println);


        // ==========
        // Self Joins
        // ==========

        // Query pairs (p1, p2) of all people where age of p1 < age of p2
        //
        // note: referencing the PersonTable.ID here is redundant. The statement
        //       could have also been written as PersonTable.on.age()
        System.out.println("\np1.age < p2.age:");
        HgDB.join(
                PersonTable.as(PersonTable.ID).on.age(),
                PersonTable.as(PERSON_ALIAS).on.age(),
                HgRelation.LT)
        .forEachRemaining(System.out::println);
    }
}
