package config;

import model.mail.Message;
import model.mail.Person;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationManagerTest {

    private ConfigurationManager myConfig;
    private List<Person> listVictimsTest;
    private List<Message> listMessagesTest;
    private List<Person> listWitnessesTest;

    @Before
    public void setUp() throws Exception {
        myConfig = new ConfigurationManager();
        listVictimsTest = new ArrayList<>();
        listMessagesTest = new ArrayList<>();
    }

    @Test
    public void getVictimsTest() {
        listVictimsTest = myConfig.getVictims();
        Assert.assertEquals( 12, listVictimsTest.size());
    }

    @Test
    public void getMessagesTest() {
        listMessagesTest = myConfig.getMessages();
        Assert.assertEquals( 3, listMessagesTest.size());
    }

    @Test
    public void getWitnessTest() {
        listWitnessesTest = myConfig.getWitness();
        Assert.assertEquals(2,listWitnessesTest.size());
    }
}