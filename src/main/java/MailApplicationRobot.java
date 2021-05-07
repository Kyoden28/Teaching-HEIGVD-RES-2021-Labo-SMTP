
import config.ConfigurationManager;
import config.IConfigurationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.prank.PrankGenerator;
import stmp.SmtpClient;

import java.util.logging.Logger;

@Data
@AllArgsConstructor
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


    }
}
