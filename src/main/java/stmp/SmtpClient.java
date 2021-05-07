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
        try {
            socket = new Socket(smtpServerAddress, smtpServerPort);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Couldn't connect to server : " + e.getMessage());
            return;
        }
        try {
            bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

            String responseStmp = bReader.readLine();
            LOG.log(Level.INFO, responseStmp);

            String responseAnalyze = null;

            if (!responseAnalyze.equals(ProtocolResponse.CODE220)) {
                responseAnalyze = ProtocolResponse.analyzeResponse(responseStmp);
                throw new CustomResponseStmpException(responseAnalyze);
            }

            pWriter.println("EHLO SmtpClient");
            pWriter.flush();

            while ((responseAnalyze = bReader.readLine()) != null) {
                System.out.println("Received : " + responseAnalyze);
                if (responseAnalyze.startsWith("250 "))
                    break;
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, "Error with buffers : " + e.getMessage());
        }

        // get from
        pWriter.println("MAIL FROM: " + prank.getGroup().getSender());
        pWriter.flush();
        LOG.log(Level.INFO, bReader.readLine());

        // get to
        int cpt = 0;
        for (Person person : prank.getGroup().getMembers()) {
            pWriter.print(person.getAddress());
            if (cpt != prank.getGroup().getMembers().size() - 1)
                pWriter.print(",");
            else
                pWriter.print("\n");
            cpt++;
        }

        // get cc


        //TODO : Connexion serveur , logique envoie message...

        //TODO: Analyse retour
    }

}
