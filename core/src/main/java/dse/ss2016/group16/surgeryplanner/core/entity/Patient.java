package dse.ss2016.group16.surgeryplanner.core.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Patient {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
	
    @Id
    private String id;
    
    @Size(max = Patient.MAX_LENGTH_DESCRIPTION)
	private String description;

    @NotEmpty
    @Size(max = Patient.MAX_LENGTH_DESCRIPTION)
    private String name;
	
    private Double lon;
    
    private Double lat;

    public Patient() {};
    
	public Patient(String name, String description) {
		this.name = name;
		this.description = description;
	}
    
	public Patient(String name, String description, Double lon, Double lat) {
		this.name = name;
		this.description = description;
		this.lon = lon;
		this.lat = lat;
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
 
	public Double getLon() {
		return lon;
	}

	public Double getLat() {
		return lat;
	}

	public static int getMaxLengthDescription() {
		return MAX_LENGTH_DESCRIPTION;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}
	
	public Patient withDescription(String description) {
		this.description = description;
		return this;
	}

	public Patient withName(String name) {
		this.name = name;
		return this;
	}

	public Patient withLon(Double lon) {
		this.lon = lon;
		return this;
	}

	public Patient withLat(Double lat) {
		this.lat = lat;
		return this;
	}
	
}