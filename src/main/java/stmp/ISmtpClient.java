package stmp;

import Utils.CustomResponseStmpException;
import model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {

    public void sendMessage(Message message) throws IOException, CustomResponseStmpException;
}
