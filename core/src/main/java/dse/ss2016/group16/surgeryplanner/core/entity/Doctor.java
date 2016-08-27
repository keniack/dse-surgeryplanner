package dse.ss2016.group16.surgeryplanner.core.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:42:29 PM
 *
 */
public class Doctor {
	public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
	
    @Id
    private String id;
    
    @Size(max = Doctor.MAX_LENGTH_DESCRIPTION)
	private String description;
	
    @NotEmpty
    @Size(max = Doctor.MAX_LENGTH_TITLE)
    private String name;

    public Doctor(){}
    
    public Doctor(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public String getId() {
		return id;
	}
    
    public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}
	
	public Doctor withDescription(String description) {
		this.description = description;
		return this;
	}

	public Doctor withName(String name) {
		this.name = name;
		return this;
	}
 
}
