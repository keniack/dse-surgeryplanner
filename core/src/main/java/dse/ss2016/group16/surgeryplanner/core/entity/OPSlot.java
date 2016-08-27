package dse.ss2016.group16.surgeryplanner.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dse.ss2016.group16.surgeryplanner.core.enums.OPType;
import dse.ss2016.group16.surgeryplanner.core.enums.SlotStatus;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:49:17 PM
 *
 */
@Document
public class OPSlot implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5541459563930684734L;

	@Id
	private String id;
	
	private String type;
	
	private String doctorId;
	private String doctorName;
	
	private String patientId;
	private String patientName;
	
	private String hospitalId;
	private String hospitalName;
	
	private String reference;
	
	private Date startTime;

	private Date endTime;
	
	private String status;
	
	
	public OPSlot() {
		
	}
	public OPSlot(String slotId) {
		this.id=slotId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public OPType getOPType() {
		return OPType.parse(type);
	}

	public void setOPType(OPType type) {
		if(type!=null)
			this.type = type.toString();
	}

	public boolean hasConflict(Date startDate, Date endDate, OPType type){
		if(startDate==null || endDate ==null )
			throw new IllegalArgumentException("start or end Date is null");

		if (startDate.getTime() >= this.startTime.getTime() && startDate.getTime() <= this.endTime.getTime() 
				&& this.type.equalsIgnoreCase(type.toString()))
			return true;
		if (endDate.getTime() >= this.startTime.getTime() && endDate.getTime() <= this.endTime.getTime() 
				&& this.type.equalsIgnoreCase(type.toString()))
			return true;
		return false;
	}
	
	public Date getEndTime() {
		return endTime;
	}


	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}



	public Date getStartTime() {
		return startTime;
	}
	
	@JsonIgnore
	public long getDuration() {
		return endTime.getTime() - startTime.getTime();
	}
	@JsonIgnore
	public long getDurationInMinutes(){
		long diff = endTime.getTime() - startTime.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(diff);
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public SlotStatus getStatus() {
		return SlotStatus.parse(status);
	}

	public void setStatus(SlotStatus status) {
		if(status!=null)
			this.status = status.toString();
	}
	
	@Override
    public String toString() {
        return String.format(
                "Slot[id=%s, startTime='%s', endTime='%s', status='%s', hospital='%s',  doctor='%s',  patient='%s' ]",
                id, startTime!=null?startTime.toString():"", endTime!=null?endTime.toString():"", status, hospitalId, doctorId, patientId);
    }

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public String getPatientId() {
		return patientId;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}
	public String getDoctorName() {
		return doctorName;
	}
	public String getPatientName() {
		return patientName;
	}
	public String getHospitalName() {
		return hospitalName;
	}
	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	
	
}
