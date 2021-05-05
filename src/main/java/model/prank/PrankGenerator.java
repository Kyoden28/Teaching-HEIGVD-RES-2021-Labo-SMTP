package model.prank;

import config.ConfigurationManager;
import config.IConfigurationManager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import model.mail.Person;
@Getter
@Setter
@AllArgsConstructor
public class PrankGenerator {
    private IConfigurationManager configurationManager;

    public void createPranks(){

        //Get victims
        configurationManager.getVictims();

        //By groups of victimes , generate a prank... ?

    }

}
