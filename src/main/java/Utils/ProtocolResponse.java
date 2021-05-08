package Utils;

/**
 * Class ProtocolResponse
 * Concerns a utility for the stmp protocol
 * @authors Christian Gomes & Johann Werkle
 */
public class ProtocolResponse {

    public static final String CODE220 = "220";
    public static final String CODE250 = "250";
    private static final String CODE354 = "354";
    private static final String CODE421 = "421";
    private static final String CODE452 = "452";
    private static final String CODE550 = "550";
    private static final String CODE554 = "554";


    private static final String ERROR250 = "The requested action taken and completed";
    private static final String ERROR354 = "Waiting for the contents of the message";
    private static final String ERROR421 = "The service is unavailable, try again later";
    private static final String ERROR452 = "The command was aborted because there isn’t enough server storage";
    private static final String ERROR550 = "The requested command failed because the user’s mailbox was unavailable or the recipient server rejected the message due to high probability of spam";
    private static final String ERROR554 = "The transaction failed";


    /**
     * Analyze response and display message
     * @param response
     * @return String response of protocol formatted
     */
    public static String analyzeResponse(String response){
        switch (response){
            case CODE250:
                return ERROR250;
            case CODE354:
                return ERROR354;
            case CODE421:
                return ERROR421;
            case CODE452:
                return ERROR452;
            case CODE550:
                return ERROR550;
            case CODE554:
                return ERROR554;
        }
        return response;
    }

}
