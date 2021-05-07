package model.prank;

import config.IConfigurationManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.mail.Group;
import model.mail.Message;
import model.mail.Person;
import stmp.SmtpClient;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class Prank {

    private final Group group;
    private Message message;
    private String from;
    private String subject;
    private String body;

    //Prank - Message
    /**
     * Play the prank
     *
     * @param configurationManager pranks configuration
     */
    public Message generatePrankMessage(IConfigurationManager configurationManager){
       // Message message = new Message(this.from, group.getMembers(), /*group.getCc(),group.getCci(),*/ this.subject, this.body);
        //message.send(new SmtpClient(configurationManager.getSmtpServerAddress(), configurationManager.getSmtpServerPort()));
        return message;
    }

}
