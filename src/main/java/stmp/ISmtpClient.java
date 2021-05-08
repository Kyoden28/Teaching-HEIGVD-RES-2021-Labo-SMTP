package stmp;

import Utils.CustomResponseStmpException;
import model.prank.Prank;

import java.io.IOException;

/**
 * Interface class for StmpClient
 * @authors Christian Gomes & Johann Werkle
 */
public interface ISmtpClient {
    public void sendPrank(Prank prank) throws IOException, CustomResponseStmpException;
}
