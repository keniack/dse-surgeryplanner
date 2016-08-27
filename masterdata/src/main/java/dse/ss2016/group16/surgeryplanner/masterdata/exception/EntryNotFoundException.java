package dse.ss2016.group16.surgeryplanner.masterdata.exception;


public class EntryNotFoundException extends Exception {

	public EntryNotFoundException(String id) {
		super();
		this.id = id;
	}

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
