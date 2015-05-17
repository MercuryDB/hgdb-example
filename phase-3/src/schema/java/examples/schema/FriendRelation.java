package examples.schema;

import com.github.mercurydb.annotations.HgValue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FriendRelation {
    private Person person;
    private List<Person> friends;

    public FriendRelation(Person person, Person... friends) {
        this.person = person;
        this.friends = Arrays.asList(friends);
    }

    @HgValue("person")
    public Person getPerson() {
        return person;
    }

    @HgValue("friends")
    public List<Person> getFriends() {
        return friends;
    }

    public String toString() {
        return "(Friend | " + person.getName() + ") = " +
                friends.stream().map(p -> p.getName())
                        .collect(Collectors.joining(", "));
    }
}
