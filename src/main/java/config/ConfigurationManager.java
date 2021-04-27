package config;

import model.mail.Person;

import java.util.List;

public class ConfigurationManager implements IConfigurationManager{

    private String smtpServerAddr;
    private int smtpServerPort;
    private final List<Person> victims;
    private final List<String> message;


    public ConfigurationManager(String smtpServerAddr, int smtpServerPort, List<Person> victims, List<String> message) {
        this.smtpServerAddr = smtpServerAddr;
        this.smtpServerPort = smtpServerPort;
        this.victims = victims;
        this.message = message;
    }
}
