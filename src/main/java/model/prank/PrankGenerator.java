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
public class PrankGenerator {

    private IConfigurationManager configurationManager;
    private static final Logger LOG = Logger.getLogger(PrankGenerator.class.getName());
    //Create pranks
    public List<Prank> createPranks(){

        //Get from
        Person sender;
        //Get victims
        List<Person> victims = configurationManager.getVictims();
        //Get messages
        List<Message> messages = configurationManager.getMessages();
        //Get witness
        List<Person> witness = configurationManager.getWitness();
        //Get numberGroups
        int numberOfGroup = configurationManager.getNumberofGroup();
        if (numberOfGroup < 1){
            //Limitation of victims
            numberOfGroup = 1;
            LOG.log(Level.INFO, "The number of groupss specified is too low. Therefore, it will be automatically set at one.");
        }
        int numberOfPeopleByGroup = configurationManager.getNumberOfPeopleByGroup();

        if (numberOfPeopleByGroup >= victims.size()){
            //Limitation of victims
            numberOfPeopleByGroup = victims.size()-1;
            LOG.log(Level.INFO, "The number of victims specified exceeds the number of victims in total. Therefore, it will be automatically reduced.");
        }

        //Create groups of people
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < numberOfGroup; i++) {
            //Random
            Collections.shuffle(victims);
            // Set the sender from the victims pool
            List<Person> recipients = victims.subList(0, numberOfPeopleByGroup);
            sender = recipients.remove(0);
            groups.add(new Group(recipients, witness , sender));
        }

        List<Prank> listOfPrank = new ArrayList<>();
        //By groups of victims , generate a prank
        for (Group group : groups) {
            Prank newPrank = new Prank();
            //Randomize the messages
            Collections.shuffle(messages);
            //Set message for prank
            newPrank.setMessage(messages.get(0));
            newPrank.setGroup(group);
            listOfPrank.add(newPrank);
        }

        return listOfPrank;
    }

}


//TODO : GET/SET TO DATA