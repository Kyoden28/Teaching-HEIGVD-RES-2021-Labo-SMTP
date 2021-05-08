package model.mail;


import lombok.Data;

@Data
/**
 * Person Class
 * Concerns a person, with his information
 * @authors Christian Gomes & Johann Werkle
 */
public class Person {

    private final String address;

    public Person(String address) {
        this.address = address;
    }
}
