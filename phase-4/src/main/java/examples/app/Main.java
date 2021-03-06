package examples.app;

import com.github.mercurydb.queryutils.*;
import examples.db.FamilyTable;
import examples.db.PersonTable;
import examples.schema.Department;
import examples.schema.Family;
import examples.schema.Person;

import java.time.LocalDate;
import java.time.Month;

public class Main {

    /**
     * We create Aliases to be used in joins here. Note that we cannot use createAlias() here. See
     * the documentation for TableID to understand why. Static ID's must be permanent names, and
     * temporary aliases must be aliases.
     */
    public static final TableID<Person> PERSON_ALIAS = TableID.createName();
    public static final TableID<Family> FAMILY_ALIAS_0 = TableID.createName();

    public static void main(String[] args) {
        buildSampleDatabase();

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
        HgDB.query(PersonTable.ge.name("I"))
                .forEachRemaining(System.out::println);

        // Query all people that are male and less than 23
        System.out.println("\nmale and age < 23:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.lt.age(23)
        ).forEachRemaining(System.out::println);

        // Query all people whose age is divisible by 2
        System.out.println("\nage % 2 == 0:");
        HgDB.query(PersonTable.predicate(p -> p.getAge() % 2 == 0))
                .forEachRemaining(System.out::println);

        // Query all families with more than 3 children
        HgDB.query(FamilyTable.predicate(f -> f.getChildren().size() > 3))
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
                HgRelation.LT
        ).forEachRemaining(System.out::println);

        // Query all people with the same birthday month
        System.out.println("\np1.birthday.month == p2.birthday.month");
        HgDB.join(
                PersonTable.on.birthday(),
                PersonTable.as(PERSON_ALIAS).on.birthday(),
                (LocalDate d1, LocalDate d2) -> {
                    Month d1Month = d1.getMonth();
                    Month d2Month = d2.getMonth();
                    return d1Month.equals(d2Month);
                }
        ).forEachRemaining(System.out::println);

        // Query pairs (p1, p2) of all people p1 is grandfather of p2
        System.out.println("\np1 == grandson of p2");
        HgDB.join(
                FamilyTable.as(FamilyTable.ID).on.father(),
                FamilyTable.as(FAMILY_ALIAS_0).on.children(),
                HgRelation.IN
        ).forEachRemaining(t -> {
            Family f1 = t.get(FamilyTable.ID);
            Family f2 = t.get(FAMILY_ALIAS_0);
            System.out.print(f2.getFather().getName() + " is grandfather of ");
            f1.getChildren().forEach(child -> System.out.print(child.getName() + ", "));
            System.out.println();
        });

        // Query pairs (p1, p2) of all people where p1 is great grandfather of p2
        System.out.println("\np1 == great grandson of p2");
        TableID<Family> FAMILY_ALIAS_1 = TableID.createAlias();
        HgDB.join(
                new JoinPredicate(
                        FamilyTable.as(FamilyTable.ID).on.father(),
                        FamilyTable.as(FAMILY_ALIAS_0).on.children(),
                        HgRelation.IN),
                new JoinPredicate(
                        FamilyTable.as(FAMILY_ALIAS_0).on.father(),
                        FamilyTable.as(FAMILY_ALIAS_1).on.children(),
                        HgRelation.IN
                )
        ).forEachRemaining(t -> {
            Family f1 = t.get(FamilyTable.ID);
            Family f2 = t.get(FAMILY_ALIAS_1);
            System.out.print(f2.getFather().getName() + " is great grandfather of ");
            f1.getChildren().forEach(child -> System.out.print(child.getName() + ", "));
            System.out.println();
        });
    }

    private static void buildSampleDatabase() {
        // Our nice little database. The constructor bytecode hooks will insert the
        // people into the database!

        // 1st generation families
        // the Doe family
        Person johnDoe = new Person("John Doe", "02/05/1945", false);  // parent
        Person rhianneDoe = new Person("Rhianne Doe", "06/06/1944", true);  // parent
        Person kimDoe = new Person("Kim Doe", "11/02/1960", false);
        Person jessDoe = new Person("Jess Doe", "09/03/1962", true);
        Person jonDoe = new Person("Jon Doe", "09/26/1958", false);
        new Family(rhianneDoe, johnDoe, kimDoe, jessDoe, jonDoe);

        // the Marven family
        Person blakeMarven = new Person("Blake Marven", "01/13/1947", false); // parent
        Person ashMarven = new Person("Ash Marven", "02/01/1948", true);  // parent
        Person capMarven = new Person("Cap Marven", "05/05/1960", false);
        new Family(ashMarven, blakeMarven, capMarven);

        // the Stuart family
        Person steveStuart = new Person("Steve Stuart", "07/03/1940", false);  // parent
        Person christyStuart = new Person("Christy Stuart", "08/09/1943", true);  // parent
        Person jimStuart = new Person("Jim Stuart", "01/30/1963", false);
        Person alleyStuart = new Person("Alley Stuart", "02/15/1967", true);
        new Family(christyStuart, steveStuart, jimStuart);

        // 2nd generation families
        // another Marven family
        Person carrieMarven = new Person("Carrie Marven", "05/28/1980", true);
        new Family(jessDoe, capMarven, carrieMarven);

        // another Stuart family
        Person loganStuart = new Person("Logan Stuart", "04/22/1984", false);
        Person willStuart = new Person("Will Stuart", "10/10/1988", false);
        new Family(kimDoe, jimStuart, loganStuart, willStuart);

        // another Doe family
        Person kaylaDoe = new Person("Kayla Doe", "09/26/1984", true);
        new Family(ashMarven, jonDoe, kaylaDoe);

        // 3rd generation families
        Person mathewStuart = new Person("Mathew Stuart", "12/12/2000", false);
        new Family(carrieMarven, loganStuart, mathewStuart);
    }
}
