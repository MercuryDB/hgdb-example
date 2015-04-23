package examples.app;

import examples.db.PersonTable;
import examples.schema.Person;
import org.mercurydb.queryutils.HgDB;
import org.mercurydb.queryutils.HgStream;

public class Main {
    public static void main(String[] args) {
        Person[] people = {
                new Person("John Doe", 20, true),
                new Person("Rhianne Styles", 21, false),
                new Person("Cole Stewart", 23, true),
                new Person("Doug Ilijev", 24, true),
        };

        HgStream<Person> stream = HgDB.query(PersonTable.ge.age(21));
        stream.forEachRemaining(System.out::println);
    }
}
