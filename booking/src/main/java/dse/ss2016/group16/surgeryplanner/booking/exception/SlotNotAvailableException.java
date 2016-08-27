package dse.ss2016.group16.surgeryplanner.booking.exception;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:38:50 PM
 *
 */
public class SlotNotAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1355459905896690746L;
	
	String id;
	
	public SlotNotAvailableException(String id){
		super();
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
