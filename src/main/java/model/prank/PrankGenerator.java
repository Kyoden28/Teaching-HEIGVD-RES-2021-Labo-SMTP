package model.prank;

import config.ConfigurationManager;
import config.IConfigurationManager;
import model.mail.Person;

public class PrankGenerator {


    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }

    public void createPranks(){

        //Get victims
        configurationManager.getVictims();

        //By groups of victimes , generate a prank... ?

    }

}
