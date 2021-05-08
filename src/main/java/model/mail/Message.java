package model.mail;

import lombok.*;
import stmp.SmtpClient;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * Message Class
 * Concerns a message that will be sent
 */
public class Message {
    private String subject;
    private String body;
}
