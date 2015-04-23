package examples.app;

import examples.schema.Person;

public class Main {
    public static void main(String[] args) {
        Person p = new Person("John Doe", 24, false);
        System.out.println(p);
    }
}
