package model.mail;

import lombok.*;
import stmp.SmtpClient;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String from;
    private ArrayList<String> to = new ArrayList<String>();
    //private ArrayList<String> cc = new ArrayList<String>();
    //private ArrayList<String> cci = new ArrayList<String>();
    private String subject;
    private String body;

    @SneakyThrows
    public void send(SmtpClient smtpClient) {
        smtpClient.sendMessage(this);
    }
}
