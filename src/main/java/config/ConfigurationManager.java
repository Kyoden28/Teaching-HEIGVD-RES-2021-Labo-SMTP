package config;

import model.mail.Message;
import model.mail.Person;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/*
 *
 */
public class ConfigurationManager implements IConfigurationManager {

    private String smtpServerAddr;
    private int smtpServerPort;
    private List<Person> victims;
    private List<String> messages;
    private int numberOfGroups;
    private List<Person> witnessesToCC;


    public ConfigurationManager() {

        try {
            messages = initializeMessages("./config/messages.utf8");
            victims = initializeVictims("./config/victims.utf8");
            initializeProperties("./config/configuration.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeProperties(String filename) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(filename);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        this.smtpServerAddr = properties.getProperty("stmpServerAddress");
        this.smtpServerPort = Integer.parseInt(properties.getProperty("smtpServerPort"));
        this.numberOfGroups = Integer.parseInt(properties.getProperty("numberOfGroups"));
        this.witnessesToCC = new ArrayList<>();
        String witnesstoCC = properties.getProperty("witnessesToCC");
        List<String> witnessesAdress = Arrays.asList(witnesstoCC.split(";"));
        for (String witnessAdress : witnessesAdress) {
            this.witnessesToCC.add(new Person(witnessAdress));
        }

    }

    private List<String> initializeMessages(String filename) {

        List<String> listMessages = new ArrayList<>();


        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;

            //TODO : Tester en débug + voir pour ameliorer directement en List<Message> - Subject
            while ((line = reader.readLine()) != null) {
                StringBuilder body = new StringBuilder();
                while (line != null && (!line.equals("=="))) {
                    body.append(line).append("\r\n");
                    line = reader.readLine();
                }
                listMessages.add(body.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listMessages;
    }

    private List<Person> initializeVictims(String filename) {

        List<Person> listVictims = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            listVictims = new ArrayList<>();
            String mailAdress;

            while ((mailAdress = reader.readLine()) != null) {
                listVictims.add(new Person(mailAdress));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listVictims;

    }

    @Override
    public List<Person> getVictims() {
        return victims;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public List<Person> getWitness() {
        return witnessesToCC;
    }

}