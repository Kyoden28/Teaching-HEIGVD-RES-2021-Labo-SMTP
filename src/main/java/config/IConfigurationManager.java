package config;

import model.mail.Message;
import model.mail.Person;

import java.util.List;

/**
 * Interface class for Configuration Manager
 * @authors Christian Gomes & Johann Werkle
 */
public interface IConfigurationManager {

    public List<Person> getVictims();

    public List<Message> getMessages();

    public List<Person> getWitness();

    public int getNumberofGroup();

    public int getNumberOfPeopleByGroup();

    public int getSmtpServerPort();

    public String getStmpServerAddress();

}
