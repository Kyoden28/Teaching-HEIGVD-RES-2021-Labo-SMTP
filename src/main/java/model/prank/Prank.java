package model.prank;

import config.ConfigurationManager;
import config.IConfigurationManager;
import lombok.*;
import model.mail.Group;
import model.mail.Message;
import model.mail.Person;
import stmp.SmtpClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



@AllArgsConstructor
@NoArgsConstructor
@Data
/**
 * Prank Class
 * Concerns the content of a prank
 * @authors Christian Gomes & Johann Werkle
 */
public class Prank {

    private Group group;
    private Message message;

}
