package stmp;

import Utils.CustomResponseStmpException;
import Utils.ProtocolResponse;
import model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class SmtpClient implements ISmtpClient {

    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());

    private String smtpServerAddress;
    private int smtpServerPort;




    public SmtpClient(String smtpServerAdress, int smtpServerPort) {
        this.smtpServerAddress = smtpServerAdress;
        this.smtpServerPort = smtpServerPort;
    }




    public void sendMessage(Message message) throws CustomResponseStmpException, IOException {

        //Connexion via socket
        Socket socket = new Socket(smtpServerAddress,smtpServerPort);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String responseStmp = reader.readLine();

        String responseAnalyze = null;

        if(!responseAnalyze.equals(ProtocolResponse.CODE220)){
            responseAnalyze = ProtocolResponse.analyzeResponse(responseStmp);
            throw new CustomResponseStmpException(responseAnalyze);
        }
        //TODO : Connexion serveur , logique envoie message...

        //TODO: Analyse retour

    }
}
