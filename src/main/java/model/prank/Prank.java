package model.prank;

import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {

    private Person sender;
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> witnessRecipients = new ArrayList<>();
    private String message;

    //Prank - Message

    public Message generatePrankMessage(){
        return null;
    }

}
