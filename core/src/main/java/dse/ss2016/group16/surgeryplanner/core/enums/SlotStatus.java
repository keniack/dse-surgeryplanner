package dse.ss2016.group16.surgeryplanner.core.enums;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 7:21:58 PM
 *
 */
public enum SlotStatus {
	
	AVAILABLE,
	RESERVED;
	
	
	public static SlotStatus parse(String value){
		if(value == null) return null;
		for(SlotStatus sp:SlotStatus.values()){
			if (value.equalsIgnoreCase(sp.toString()))
				return sp;
		}
		return null;
	}
}
