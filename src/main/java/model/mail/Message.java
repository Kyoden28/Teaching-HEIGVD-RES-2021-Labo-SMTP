package model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import stmp.SmtpClient;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class Message {

    private String from;
    private ArrayList<Person> to;
    //private ArrayList<Person> cc;
    //private ArrayList<Person> cci;
    private String subject;
    private String body;

    @SneakyThrows
    public void send(SmtpClient smtpClient) {
        smtpClient.sendMessage(this);
    }
}
