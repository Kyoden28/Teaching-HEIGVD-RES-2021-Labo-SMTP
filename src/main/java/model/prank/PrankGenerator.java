package model.prank;

import config.IConfigurationManager;
import lombok.AllArgsConstructor;
import lombok.Data;
import model.mail.Group;
import model.mail.Message;
import model.mail.Person;
import stmp.SmtpClient;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
@AllArgsConstructor
/**
 * PrankGenerator Class
 * Allows the generation of pranks mail
 * @authors Christian Gomes & Johann Werkle
 */
public class PrankGenerator {

    private IConfigurationManager configurationManager;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    /**
     * Allows the generation of pranks
     * @return List of pranks
     */
    public List<Prank> createPranks(){


        //Get informations
        Person sender;
        List<Person> victims = configurationManager.getVictims();
        List<Message> messages = configurationManager.getMessages();
        List<Person> witness = configurationManager.getWitness();
        int numberOfGroup = configurationManager.getNumberofGroup();

        //Checking the limitations
        if (numberOfGroup < 1){
            numberOfGroup = 1;
            LOG.log(Level.INFO, "The number of groupss specified is too low. Therefore, it will be automatically set at one.");
        }

        int numberOfPeopleByGroup = configurationManager.getNumberOfPeopleByGroup();

        //Checking the limitations
        if (numberOfPeopleByGroup >= victims.size()){
            numberOfPeopleByGroup = victims.size()-1;
            LOG.log(Level.INFO, "The number of victims specified exceeds the number of victims in total. Therefore, it will be automatically reduced.");
        }

        //Create groups of people
        List<Group> groups = new ArrayList<>();
        List<Person> recipients;
        for (int i = 0; i < numberOfGroup; i++) {
            recipients = new ArrayList<>();
            //Random
            Collections.shuffle(victims);
            sender = victims.remove(0);
            for(int k = 0; k < numberOfPeopleByGroup - 1; k++){
                recipients.add(victims.get(k));
            }
            groups.add(new Group(recipients, witness , sender));
        }

        //Creation of pranks by group
        List<Prank> listOfPrank = new ArrayList<>();
        for (Group group : groups) {
            Prank newPrank = new Prank();
            //Randomize the messages
            Collections.shuffle(messages);
            newPrank.setMessage(messages.get(0));
            newPrank.setGroup(group);
            listOfPrank.add(newPrank);
        }

        return listOfPrank;
    }

}