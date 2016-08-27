package dse.ss2016.group16.surgeryplanner.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 10.06.2016.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No free OP-Slot in given timeframe")
public class OPSlotNotFoundException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4491210773270079831L;

	public OPSlotNotFoundException(){
        super();
    }

	public OPSlotNotFoundException(String string,Throwable e) {
		super(string,e);
	}
	public OPSlotNotFoundException(String string) {
		super(string);
	}

}
