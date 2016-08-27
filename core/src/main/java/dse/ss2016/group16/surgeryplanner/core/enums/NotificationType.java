package dse.ss2016.group16.surgeryplanner.core.enums;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 12.06.2016.
 *
 * Denotes if a Notification is for a Doctor, Patient or Hospital
 */
public enum NotificationType {

    DOCTOR,
    PATIENT,
    HOSPITAL;
	
	public static NotificationType parse(String value){
		if(value == null) return null;
		for(NotificationType sp:NotificationType.values()){
			if (value.equalsIgnoreCase(sp.toString()))
				return sp;
		}
		return null;
	}
	
}
