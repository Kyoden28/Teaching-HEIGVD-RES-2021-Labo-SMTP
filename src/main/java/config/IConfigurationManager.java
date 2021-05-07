package config;

import model.mail.Message;
import model.mail.Person;

import java.util.List;

public interface IConfigurationManager {

    public List<Person> getVictims();

    public List<Message> getMessages();

    public List<Person> getWitness();

}
