package Utils;


import lombok.Data;

@Data
/**
 * Class CustomResponseStmpException
 * @authors Christian Gomes & Johann Werkle
 */
public class CustomResponseStmpException extends Exception {

    private String message;

    /**
     * Constructor of CustomResponseStmpException
     */
    public CustomResponseStmpException(String message){
        super(message);
    }

}
