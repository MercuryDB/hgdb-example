package examples.app;

import com.github.mercurydb.queryutils.*;
import com.github.mercurydb.queryutils.graph.HgClosureGraph;
import examples.db.*;
import examples.schema.FamilyRelation;
import examples.schema.FamilyEvent;
import examples.schema.FriendRelation;
import examples.schema.Person;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class Main {

    /**
     * We create Aliases to be used in joins here. Note that we cannot use createAlias() here. See
     * the documentation for TableID to understand why. Static ID's must be permanent names, and
     * temporary aliases must be aliases.
     */
    public static final TableID<Person> PERSON_ALIAS = TableID.createName();
    public static final TableID<FamilyRelation> FAMILY_ALIAS_0 = TableID.createName();

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
        stream.forEach(System.out::println);

        //  Query all people with first names that begin with a letter after I
        System.out.println("\nname >= 'I':");
        HgDB.query(PersonTable.ge.name("I"))
                .forEach(System.out::println);

        // Query all people that are male and less than 23
        System.out.println("\nmale and age < 23:");
        HgDB.query(
                PersonTable.eq.gender(Person.Gender.MALE),
                PersonTable.lt.age(23)
        ).forEach(System.out::println);

        // Query all people whose age is divisible by 2
        System.out.println("\nage % 2 == 0:");
        HgDB.query(PersonTable.predicate(p -> p.getAge() % 2 == 0))
                .forEach(System.out::println);

        // Query all families with >= 3 children
        System.out.println("\nfamilies with >= 3 children:");
        HgDB.query(FamilyRelationTable.predicate(f -> f.getChildren().size() >= 3))
                .forEach(System.out::println);

        // Query all family events in the 1990's
        System.out.println("\nfamily events in the 1990's:");
        LocalDate firstOf1990 = LocalDate.of(1990, 1, 1);
        LocalDate firstOf2000 = LocalDate.of(2000, 1, 1);
        HgDB.query(
                FamilyEventTable.ge.date(firstOf1990),
                FamilyEventTable.lt.date(firstOf2000)
        ).forEach(System.out::println);

        // =================
        // Multi-Table Joins
        // =================

        System.out.println("\nPeople born on the same day as an event:");
        HgDB.join(
                PersonTable.on.birthday(),
                FamilyEventTable.on.date()
        ).forEach(System.out::println);

        System.out.println("\nPeople that were teenagers during an event");
        HgDB.join(
                PersonTable.on.birthday(),
                FamilyEventTable.on.date(),
                (LocalDate d1, LocalDate d2) -> {
                    long diff = ChronoUnit.YEARS.between(d1, d2);
                    return diff < 20 && diff > 10;
                }
        ).forEach(tuple -> {
            Person p = tuple.get(PersonTable.ID);
            FamilyEvent fe = tuple.get(FamilyEventTable.ID);
            System.out.println(p.getName() + " (" + p.getAge() + ")" + " | " + fe);
        });

        // ==========
        // Self Joins
        // ==========

        // Query pairs (p1, p2) of all people where age of p1 < age of p2
        //
        // note: referencing the PersonTable.ID here is redundant. The statement
        //       could have also been written as PersonTable.on.age()

        // Query all people with the same birthday month
        System.out.println("\np1.birthday.month == p2.birthday.month");
        HgDB.join(
                PersonTable.as(PersonTable.ID).on.birthday(),
                PersonTable.as(PERSON_ALIAS).on.birthday(),
                (LocalDate d1, LocalDate d2) -> {
                    Month d1Month = d1.getMonth();
                    Month d2Month = d2.getMonth();
                    return d1Month.equals(d2Month);
                }
        ).forEach(System.out::println);

        // Query all people who have friends whose parents are also friends
        System.out.println("\nAll people who have friends whose parents are also friends");
        TableID<FriendRelation> friendId1 = TableID.createAlias();
        TableID<FamilyRelation> familyId1 = TableID.createAlias();
        TableID<FriendRelation> friendId2 = TableID.createAlias();
        TableID<FriendRelation> friendId3 = TableID.createAlias();
        HgDB.join(
                new JoinPredicate(
                        FriendRelationTable.as(friendId1).on.person(),
                        FamilyRelationTable.as(familyId1).on.children(), HgRelation.IN),
                new JoinPredicate(
                        FamilyRelationTable.as(familyId1).on.father(),
                        FriendRelationTable.as(friendId2).on.person()),
                new JoinPredicate(
                        FamilyRelationTable.as(familyId1).on.father(),
                        FriendRelationTable.as(friendId3).on.friends(), HgRelation.IN)
        ).forEach(System.out::println);

        System.out.println("\nFAMILY TRANSITIVE CLOSURE");
        HgClosureGraph graph = new HgClosureGraph(
                FamilyRelationTable.as(FamilyRelationTable.ID).on.father(),
                FamilyRelationTable.as(FAMILY_ALIAS_0).on.children(),
                HgRelation.IN);

        HgDB.join(graph.transitiveClosurePredicate()).forEach(t -> {
            FamilyRelation f1 = t.get(FamilyRelationTable.ID);
            FamilyRelation f2 = t.get(FAMILY_ALIAS_0);
            System.out.print(f2.getFather().getName() + " is ascendant of ");
            f1.getChildren().forEach(child -> System.out.print(child.getName() + ", "));
            System.out.println();
        });

        // Query pairs (p1, p2) of all people p1 is grandfather of p2
        System.out.println("\np1 == grandson of p2");
        HgDB.join(
                FamilyRelationTable.as(FamilyRelationTable.ID).on.father(),
                FamilyRelationTable.as(FAMILY_ALIAS_0).on.children(),
                HgRelation.IN
        ).forEach(t -> {
            FamilyRelation f1 = t.get(FamilyRelationTable.ID);
            FamilyRelation f2 = t.get(FAMILY_ALIAS_0);
            System.out.print(f2.getFather().getName() + " is grandfather of ");
            f1.getChildren().forEach(child -> System.out.print(child.getName() + ", "));
            System.out.println();
        });

        // Query pairs (p1, p2) of all people where p1 is great grandfather of p2
        System.out.println("\np1 == great grandson of p2");
        TableID<FamilyRelation> FAMILY_ALIAS_1 = TableID.createAlias();
        HgDB.join(
                new JoinPredicate(
                        FamilyRelationTable.as(FamilyRelationTable.ID).on.father(),
                        FamilyRelationTable.as(FAMILY_ALIAS_0).on.children(),
                        HgRelation.IN),
                new JoinPredicate(
                        FamilyRelationTable.as(FAMILY_ALIAS_0).on.father(),
                        FamilyRelationTable.as(FAMILY_ALIAS_1).on.children(),
                        HgRelation.IN
                )
        ).forEach(t -> {
            FamilyRelation f1 = t.get(FamilyRelationTable.ID);
            FamilyRelation f2 = t.get(FAMILY_ALIAS_1);
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
        FamilyRelation doe1 = new FamilyRelation(rhianneDoe, johnDoe, kimDoe, jessDoe, jonDoe);

        // the Marven family
        Person blakeMarven = new Person("Blake Marven", "01/13/1947", false); // parent
        Person ashMarven = new Person("Ash Marven", "02/01/1948", true);  // parent
        Person capMarven = new Person("Cap Marven", "05/05/1960", false);
        FamilyRelation marven1 = new FamilyRelation(ashMarven, blakeMarven, capMarven);

        // the Stuart family
        Person steveStuart = new Person("Steve Stuart", "07/03/1940", false);  // parent
        Person christyStuart = new Person("Christy Stuart", "08/09/1943", true);  // parent
        Person jimStuart = new Person("Jim Stuart", "01/30/1963", false);
        Person alleyStuart = new Person("Alley Stuart", "02/15/1967", true);
        FamilyRelation stuart1 = new FamilyRelation(christyStuart, steveStuart, jimStuart);

        // 2nd generation families
        // another Marven family
        Person carrieMarven = new Person("Carrie Marven", "05/28/1980", true);
        FamilyRelation marven2 = new FamilyRelation(jessDoe, capMarven, carrieMarven);

        // another Stuart family
        Person loganStuart = new Person("Logan Stuart", "04/22/1984", false);
        Person willStuart = new Person("Will Stuart", "10/10/1988", false);
        FamilyRelation stuart2 = new FamilyRelation(kimDoe, jimStuart, loganStuart, willStuart);

        // another Doe family
        Person kaylaDoe = new Person("Kayla Doe", "09/26/1984", true);
        FamilyRelation doe2 = new FamilyRelation(ashMarven, jonDoe, kaylaDoe);

        // 3rd generation families
        Person mathewStuart = new Person("Mathew Stuart", "12/12/2000", false);
        FamilyRelation stuart3 = new FamilyRelation(carrieMarven, loganStuart, mathewStuart);

        // Now we setup the FamilyEvents
        new FamilyEvent(marven2, "04/04/1980", "It's a girl! We'll name her Carrie.");
        new FamilyEvent(marven2, "05/28/1980", "Carrie's born!");
        new FamilyEvent(stuart2, "01/20/1994", "Bought Will his first computer. He's so smart!");
        new FamilyEvent(stuart2, "01/23/1994", "Will poured milk on the computer. It's ruined.");
        new FamilyEvent(stuart2, "11/25/1995", "Bought a family computer");
        new FamilyEvent(doe1, "11/19/1969", "Apollo 11 to the moon!");
        new FamilyEvent(stuart2, "11/26/1998", "Played DDR with the boys.");

        // set up some friends
        new FriendRelation(kaylaDoe, loganStuart);
        new FriendRelation(jimStuart, jonDoe);
        new FriendRelation(jonDoe, jimStuart);
    }
}
