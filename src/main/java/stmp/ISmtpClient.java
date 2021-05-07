package stmp;

import Utils.CustomResponseStmpException;
import model.mail.Message;
import model.prank.Prank;

import java.io.IOException;

public interface ISmtpClient {
    public void sendPrank(Prank prank) throws IOException, CustomResponseStmpException;

}
