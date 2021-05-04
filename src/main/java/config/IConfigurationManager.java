package config;

import model.mail.Person;

import java.util.List;

public interface IConfigurationManager {

    public List<Person> getVictims();

    public List<String> getMessages();

    public List<Person> getWitness();

}
