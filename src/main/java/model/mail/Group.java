package model.mail;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class Group {

    private final int MIN_PEOPLE_BY_GROUP = 2;
    private final List<Person> members;
    private final List<Person> cci;
    private final Person sender;

    public Group(List<Person> victims, List<Person> cci, Person sender){

        this.cci = cci;
        this.members = victims;
        this.sender = sender;
        Collections.shuffle(victims);
    }

    public void setMembers(Person person){
        members.add(person);
    }

    public List<Person> getMembers() {
        return members;
    }
}
