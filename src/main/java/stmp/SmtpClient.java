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
public class
SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    private String smtpServerAddress;
    private int smtpServerPort;
    private BufferedReader bReader = null;
    private PrintWriter pWriter = null;
    private Message message;

    public SmtpClient(String smtpServerAddress, int smtpServerPort) {
        this.smtpServerAddress = smtpServerAddress;
        this.smtpServerPort = smtpServerPort;
    }

    @Override
    public void sendPrank(Prank prank) throws CustomResponseStmpException, IOException {

        Socket socket;
        //Connexion via socket
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
            LOG.log(Level.INFO, responseAnalyze);

            if (!responseAnalyze.startsWith(ProtocolResponse.CODE220)) {
                responseAnalyze = ProtocolResponse.analyzeResponse(responseAnalyze);
                throw new CustomResponseStmpException(responseAnalyze);
            }

            pWriter.println("EHLO SmtpClient");
            pWriter.flush();

            while ((responseAnalyze = bReader.readLine()) != null) {
                System.out.println("Received : " + responseAnalyze);
                if (responseAnalyze.startsWith("250-"))
                    break;
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error with buffers : " + e.getMessage());
        }

        // get from
        pWriter.println("MAIL FROM: " + prank.getGroup().getSender().getAddress());
        pWriter.flush();
        LOG.log(Level.INFO, bReader.readLine());

        // get to

        for (Person person : prank.getGroup().getRecipients()) {
            //Set RCPT TO
            pWriter.print("RCPT TO: <");
            pWriter.print(person.getAddress() + ">\r\n");
            //Read response
            pWriter.flush();
            responseAnalyze = bReader.readLine();
            if (!responseAnalyze.startsWith("250")) {
                LOG.log(Level.INFO, "Fail to RCPT TO the adress " + person.getAddress());
            }

        }



        // get cci
        for (Person person : prank.getGroup().getCci()) {
            //Set RCPT TO
            pWriter.print("RCPT TO: <");
            pWriter.print(person.getAddress() + ">\r\n");
            pWriter.flush();
            //Read response
            responseAnalyze = bReader.readLine();
            if (!responseAnalyze.startsWith("250")) {
                LOG.log(Level.INFO, "Fail to RCPT TO the address " + person.getAddress());
            }
        }

        //Set DATA
        pWriter.println("DATA");
        responseAnalyze = bReader.readLine();
        if (!responseAnalyze.startsWith("250")) {
            LOG.log(Level.INFO, "Fail to DATA ");
        } else {
            pWriter.println("From: " + prank.getGroup().getSender().getAddress());
            pWriter.println(prank.getMessage().getSubject());


            pWriter.print("To: ");

            int numberOfRcpTo = 0;
            for (Person person : prank.getGroup().getRecipients()) {
                //Set RCP
                pWriter.print(person.getAddress());
                if (numberOfRcpTo == prank.getGroup().getRecipients().size() - 1) {
                    pWriter.print(",");
                } else {
                    pWriter.println();
                }
            }

            pWriter.flush();

            pWriter.print("Bcc: ");
            numberOfRcpTo = 0;
            for (Person person : prank.getGroup().getCci()) {
                //Set bcc
                pWriter.print(person.getAddress());
                if (numberOfRcpTo == prank.getGroup().getCci().size() - 1) {
                    pWriter.print(",");
                } else {
                    pWriter.println();
                }
            }

            pWriter.flush();

            pWriter.print("Subject: =?UTF-8?B?");
            String subject =  Base64.getEncoder().encodeToString(prank.getMessage().getSubject().getBytes(StandardCharsets.UTF_8));
            pWriter.print(subject);
            pWriter.println("?=");
            pWriter.flush();

            pWriter.println("Content-Type: text/plain; charset=utf-8");
            pWriter.flush();

            pWriter.println(prank.getMessage().getBody());
            pWriter.println(".");
            pWriter.println("QUIT");
            pWriter.flush();

            responseAnalyze = bReader.readLine();
            if (!responseAnalyze.startsWith("250")) {
                LOG.log(Level.INFO, "Fail to send mail ");

                LOG.log(Level.INFO, "The message was send successfully");

            }
        }
    }
}
