
import Utils.CustomResponseStmpException;
import config.ConfigurationManager;
import config.IConfigurationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.prank.Prank;
import model.prank.PrankGenerator;
import stmp.SmtpClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
/**
 * Class MailApplicationRobot
 * Main program of the application
 * Allows you to send pranks to a list of addresses
 * @authors Christian Gomes & Johann Werkle
 */
public class MailApplicationRobot {


    private static final Logger LOG = Logger.getLogger(MailApplicationRobot.class.getName());

    public static void main(String[] args) {

        System.out.println("Hello !");

        //Initialize configuration
        ConfigurationManager configurationManager = new ConfigurationManager();

        //Generate pranks
        PrankGenerator prankGenerator = new PrankGenerator(configurationManager);

        //Send pranks
        SmtpClient smtpClient = new SmtpClient(configurationManager.getStmpServerAddress(),configurationManager.getSmtpServerPort());
        List<Prank> pranksToSend = prankGenerator.createPranks();
        for (Prank prank : pranksToSend) {
            try {
                smtpClient.sendPrank(prank);
            } catch (CustomResponseStmpException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
