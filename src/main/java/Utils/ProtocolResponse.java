package Utils;

public class ProtocolResponse {

    public static final String CODE220 = "220";
    private static final String CODE250 = "250";
    private static final String CODE354 = "354";
    private static final String CODE421 = "421";
    private static final String CODE452 = "452";
    private static final String CODE550 = "550";
    private static final String CODE554 = "554";


    private static final String ERROR250 = "";
    private static final String ERROR354 = "";
    private static final String ERROR421 = "";
    private static final String ERROR452 = "";
    private static final String ERROR550 = "";
    private static final String ERROR554 = "";


    public static String analyzeResponse(String response){
        //TODO : A revoir conception maybe
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
