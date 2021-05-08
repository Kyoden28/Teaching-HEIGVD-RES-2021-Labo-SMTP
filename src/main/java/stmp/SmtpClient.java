package stmp;

import Utils.CustomResponseStmpException;
import Utils.ProtocolResponse;
import lombok.Data;

import model.mail.Message;
import model.mail.Person;
import model.prank.Prank;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;


@Data
/**
 * The StmpClient class allows the initiation of the connection
 * with the stmp client and the sending of information
 * @authors Christian Gomes & Johann Werkle
 */
public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort;
    private BufferedReader bReader = null;
    private PrintWriter pWriter = null;
    private Message message;

    /**
     * Constructor of stmpclient
     */
    public SmtpClient(String smtpServerAddress, int smtpServerPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    /**
     * Sending pranks to the stmp server
     * @param prank prank that we want to send
     * @throws CustomResponseStmpException
     * @throws IOException
     */
    @Override
    public void sendPrank(Prank prank) throws CustomResponseStmpException, IOException {

        Socket socket;
        String responseAnalyze;
        try {
            socket = new Socket(smtpServerAddress, smtpServerPort);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Couldn't connect to server : " + e.getMessage());
            return;
        }
        try {
            bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            responseAnalyze = bReader.readLine();

            if (!responseAnalyze.startsWith(ProtocolResponse.CODE220)) {
                responseAnalyze = ProtocolResponse.analyzeResponse(responseAnalyze);
                throw new CustomResponseStmpException(responseAnalyze);
            }

            System.out.println(responseAnalyze);

            pWriter.println("EHLO SmtpClient");
            System.out.println("EHLO SmtpClient");

            pWriter.flush();

            while ((responseAnalyze = bReader.readLine()) != null) {
                System.out.println("Received : " + responseAnalyze);
                if (responseAnalyze.startsWith(ProtocolResponse.CODE250))
                    break;
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error with buffers : " + e.getMessage());
        }

        //Details about FROM :
        System.out.println("MAIL FROM: " + prank.getGroup().getSender().getAddress());
        pWriter.println("MAIL FROM: " + prank.getGroup().getSender().getAddress());
        pWriter.flush();

        //Details about TO people
        for (Person person : prank.getGroup().getRecipients()) {
            //Set RCPT TO
            pWriter.print("RCPT TO: <");
            pWriter.print(person.getAddress() + ">\r\n");
            System.out.println("RCPT TO: <" + person.getAddress() + ">");
            //Read response
            pWriter.flush();
            responseAnalyze = bReader.readLine();
            if (!responseAnalyze.startsWith("250")) {
                LOG.log(Level.INFO, "Fail to RCPT TO the adress " + person.getAddress());
            }
            System.out.println(responseAnalyze);

        }

        //Details about CC , who is empty
        pWriter.println("RCPT TO: <>");
        pWriter.flush();

        //Details about CCi
        for (Person person : prank.getGroup().getCci()) {
                pWriter.print("RCPT TO: <");
                pWriter.print(person.getAddress() + ">\r\n");
                System.out.println("RCPT TO: <" + person.getAddress());
                pWriter.flush();
                responseAnalyze = bReader.readLine();
                if (!responseAnalyze.startsWith("250")) {
                    LOG.log(Level.INFO, "Fail to RCPT TO the address " + person.getAddress());
                }
                System.out.println(responseAnalyze);
        }

        //Details about Data
        pWriter.println("DATA");
        System.out.println("DATA");
        responseAnalyze = bReader.readLine();

        if (!responseAnalyze.startsWith("250")) {
            LOG.log(Level.INFO, "Fail to DATA ");
        } else {

            System.out.println(responseAnalyze);

            //Details about From :
            System.out.println("From: " + prank.getGroup().getSender().getAddress());
            pWriter.println("From: " + prank.getGroup().getSender().getAddress());
            pWriter.flush();

            //Details about To :
            System.out.print("To: ");
            pWriter.print("To: ");
            int numberOfRcpTo = 0;
            for (Person person : prank.getGroup().getRecipients()) {
                pWriter.print(person.getAddress());
                System.out.print(person.getAddress());
                if (numberOfRcpTo != prank.getGroup().getRecipients().size() - 1) {
                    pWriter.print(",");
                    System.out.print(",");
                    numberOfRcpTo++;
                } else {
                    pWriter.println();
                    System.out.println();
                }
            }
            pWriter.flush();

            //Details about Bcc :
            pWriter.print("Bcci: ");
            System.out.print("Bcci: ");
            numberOfRcpTo = 0;
            for (Person person : prank.getGroup().getCci()) {
                //Set bcc
                pWriter.print(person.getAddress());
                System.out.print(person.getAddress());
                if (numberOfRcpTo != prank.getGroup().getCci().size() - 1) {
                    pWriter.print(",");
                    System.out.print(",");
                    numberOfRcpTo++;
                } else {
                    pWriter.println();
                    System.out.println();
                }
            }

            pWriter.flush();

            //Send subject and body
            System.out.print("Subject: =?UTF-8?B?");
            pWriter.print("Subject: =?UTF-8?B?");
            String subject =  Base64.getEncoder().encodeToString(prank.getMessage().getSubject().getBytes(StandardCharsets.UTF_8));
            pWriter.print(subject);
            System.out.print(subject);
            System.out.println("?=");
            pWriter.println("?=");
            pWriter.flush();

            pWriter.println("Content-Type: text/plain; charset=utf-8");
            pWriter.flush();

            System.out.println(prank.getMessage().getBody());
            pWriter.println(prank.getMessage().getBody());

            System.out.println(".");
            pWriter.println(".");
            System.out.println("QUIT");
            pWriter.println("QUIT");
            pWriter.flush();

            responseAnalyze = bReader.readLine();
            System.out.println(responseAnalyze);
            if (!responseAnalyze.startsWith("250")) {
                LOG.log(Level.INFO, "Fail to send mail ");

            }else{
              //  LOG.log(Level.INFO, "The message was send successfully");
            }
        }
    }
}
