package dse.ss2016.group16.surgeryplanner.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 12.06.2016.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No corresponding notifications could be found ")
public class NotificationNotFoundException extends Exception {

    public NotificationNotFoundException(){
        super();
    }
}
