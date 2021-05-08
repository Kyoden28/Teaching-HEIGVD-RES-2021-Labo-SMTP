package config;

import model.mail.Message;
import model.mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * The configurationmanager class allows the initialization of
 * the program using the configuration files
 * @authors Christian Gomes & Johann Werkle
 */
public class ConfigurationManager implements IConfigurationManager {

    private String smtpServerAddr;
    private int smtpServerPort;
    private final List<Person> victims = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private int numberOfGroups;
    private int numberOfVictimsByGroup;
    private List<Person> witnessesToCC;


    /**
     * Constructor of ConfigurationManager
     */
    public ConfigurationManager() {
        try {
            initializeMessages("./config/messages.utf8");
            initializeVictims("./config/victims.utf8");
            initializeProperties("./config/configuration.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read properties files to configure the project
     * @param filename name of properties file
     * @throws IOException
     */
    private void initializeProperties(String filename) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        this.smtpServerAddr = properties.getProperty("stmpServerAddress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        this.numberOfVictimsByGroup =  Integer.parseInt(properties.getProperty("numberOfPeopleByGroup"));
        this.witnessesToCC = new ArrayList<>();
        String witnesstoCC = properties.getProperty("witnessestoCC");
        List<String> witnessesAdress = Arrays.asList(witnesstoCC.split(";"));
        for (String witnessAdress : witnessesAdress) {
            this.witnessesToCC.add(new Person(witnessAdress));
        }

    }

    /**
     * Read messages files for initialization
     * @param filename name of messages file
     */
    private void initializeMessages(String filename) {


        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            int messageNumber = -1;
            Message message;

            //Initialization of messages
            while (((line = reader.readLine()) != null )){
                messageNumber++;
                StringBuilder body = new StringBuilder();
                message = new Message();
                message.setSubject(line);
                messages.add(message);
                while((line = reader.readLine() )!= null && (!line.equals("==")))
                {
                    body.append(line).append("\r\n");
                }
                messages.get(messageNumber).setBody(body.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Read victims files for initialization
     * @param filename name of victims file
     */
    private void initializeVictims(String filename) {

        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            //Initialization of victims
            String mailAdress;
            while ((mailAdress = reader.readLine()) != null) {
                victims.add(new Person(mailAdress));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * @return current list of victims
     */
    @Override
    public List<Person> getVictims() {

        return victims;
    }


    /**
     * @return current list of messages
     */
    @Override
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @return current list of witness
     */
    @Override
    public List<Person> getWitness() {
        return witnessesToCC;
    }

    /**
     * @return current number of group
     */
    @Override
    public int getNumberofGroup() { return numberOfGroups; }

    /**
     * @return current number of people by group
     */
    @Override
    public int getNumberOfPeopleByGroup() { return numberOfVictimsByGroup; }

    /**
     * @return stmp server port
     */
    @Override
    public int getSmtpServerPort() {
        return this.smtpServerPort;
    }

    /**
     * @return stmp server address
     */
    @Override
    public String getStmpServerAddress() {
        return this.smtpServerAddr;
    }

}