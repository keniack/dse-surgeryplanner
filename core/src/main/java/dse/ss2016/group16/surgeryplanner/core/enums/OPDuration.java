package dse.ss2016.group16.surgeryplanner.core.enums;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 8:24:03 PM
 *
 */
public enum OPDuration {

	_60_MIN(60),
	_120_MIN(120),
	_180_MIN(180),
	_240_MIN(240);

	Integer value;
	
	OPDuration(Integer value) {
		this.value = value;
	}
	
	public static OPDuration parse(Integer value){
		if(value == null) return null;
		for(OPDuration item:OPDuration.values()){
			if (value == item.getValue())
				return item;
		}
		return null;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	
}
