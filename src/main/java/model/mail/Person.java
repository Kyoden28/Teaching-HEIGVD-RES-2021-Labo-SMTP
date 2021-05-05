package model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Person {

    private String lastName;
    private String firstName;
    private final String address;

    public String getAddress(){
        return this.address;
    }


    public Person(String witnessAdress) {
        this.address = witnessAdress;
    }
}
