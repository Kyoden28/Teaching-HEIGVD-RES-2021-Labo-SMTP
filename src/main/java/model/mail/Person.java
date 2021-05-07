package model.mail;


import lombok.Data;

@Data

public class Person {

    private final String address;

    public Person(String address) {
        this.address = address;
    }
}
