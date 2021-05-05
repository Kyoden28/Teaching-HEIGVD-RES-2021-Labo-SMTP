package model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Collections;

@Getter
@Setter
public class Group {

    private final int MIN_PEOPLE_BY_GROUP = 5;
    private final ArrayList<Person> members;
    //private final ArrayList<Person> cc = new ArrayList<Person>;
    //private final ArrayList<Person> cci;
    private final String sender;
    public Group(ArrayList<Person> victims, String sender){
        Collections.shuffle(victims);
        this.members = victims;
        this.sender = sender;
    }

    public void setMembers(Person person){
        members.add(person);
    }

    public ArrayList<Person> getMembers() {
        return members;
    }
}
