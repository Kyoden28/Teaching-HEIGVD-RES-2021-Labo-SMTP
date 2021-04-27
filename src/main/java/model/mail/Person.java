package model.mail;

public class Person {

    private String lastName;
    private String firstName;
    private final String address;

    public Person(String lastName, String firstName, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
    }
}
