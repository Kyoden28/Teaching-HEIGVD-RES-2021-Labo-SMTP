package Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseStmpException extends Exception {

    private String message;

    public CustomResponseStmpException(String message){
        super(message);

    }

}
