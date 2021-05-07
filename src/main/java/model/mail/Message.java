package model.mail;

import lombok.*;
import stmp.SmtpClient;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String subject;
    private String body;

}
