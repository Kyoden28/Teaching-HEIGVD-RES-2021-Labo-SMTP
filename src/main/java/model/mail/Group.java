package model.mail;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Data
public class Group {

    private final List<Person> recipients;
    private final List<Person> cci;
    private final Person sender;

    public Group(List<Person> victims, List<Person> cci, Person sender){
        this.cci = cci;
        this.recipients = victims;
        this.sender = sender;
    }

}
