package model.mail;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Group {

    private final int MIN_PEOPLE_BY_GROUP = 2;
    private final List<Person> recipients;
    private final List<Person> cci;
    private final Person sender;

    public Group(List<Person> victims, List<Person> cci, Person sender){

        this.cci = cci;
        this.recipients = victims;
        this.sender = sender;
        Collections.shuffle(victims);
    }

    public void setRecipients(Person person){
        recipients.add(person);
    }

}
