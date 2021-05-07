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
    private final List<Person> victims = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private int numberOfGroups;
    private int numberOfVictimsByGroup;
    private List<Person> witnessesToCC;


    public ConfigurationManager() {
        try {
            initializeMessages("./config/messages.utf8");
            initializeVictims("./config/victims.utf8");
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
        this.numberOfVictimsByGroup =  Integer.parseInt(properties.getProperty("numberOfPeopleByGroup"));
        this.witnessesToCC = new ArrayList<>();
        String witnesstoCC = properties.getProperty("witnessestoCC");
        List<String> witnessesAdress = Arrays.asList(witnesstoCC.split(";"));
        for (String witnessAdress : witnessesAdress) {
            this.witnessesToCC.add(new Person(witnessAdress));
        }

    }

    private void initializeMessages(String filename) {


        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;

            int messageNumber = -1;

            Message message = null;

            //Read subject
            //line = reader.readLine();
            //Prepare message


            while (((line = reader.readLine()) != null )){
                messageNumber++;
                StringBuilder body = new StringBuilder();
                message = new Message();
                message.setSubject(line);
                messages.add(message);
                while((line = reader.readLine() )!= null && (!line.equals("==")))
                {
                    body.append(line).append("\r\n");
                    //    line = reader.readLine();
                }
                messages.get(messageNumber).setBody(body.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeVictims(String filename) {

        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

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

    @Override
    public List<Person> getVictims() {

        return victims;
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public List<Person> getWitness() {
        return witnessesToCC;
    }

    @Override
    public int getNumberofGroup() { return numberOfGroups; }

    @Override
    public int getNumberOfPeopleByGroup() { return numberOfVictimsByGroup; }

    @Override
    public int getSmtpServerPort() {
        return this.smtpServerPort;
    }

    @Override
    public String getStmpServerAddress() {
        return this.smtpServerAddr;
    }




}