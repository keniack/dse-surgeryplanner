package dse.ss2016.group16.surgeryplanner.core.enums;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:15:44 PM
 *
 */
public enum OPType {
	
	AUGEN,
	ORTHO,
	HNO,
	NEURO,
	KARDIO;
	
	public static OPType parse(String value){
		if(value == null) return null;
		for(OPType sp:OPType.values()){
			if (value.equalsIgnoreCase(sp.toString()))
				return sp;
		}
		return null;
	}
	

}
