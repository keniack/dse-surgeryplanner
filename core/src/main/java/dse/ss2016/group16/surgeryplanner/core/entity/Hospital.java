package dse.ss2016.group16.surgeryplanner.core.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public final class Hospital {

    public static final int MAX_LENGTH_DESCRIPTION = 500;
    public static final int MAX_LENGTH_TITLE = 100;
	
    @Id
    private String id;
    
    @Size(max = Hospital.MAX_LENGTH_DESCRIPTION)
	private String description;

    @NotEmpty
    @Size(max = Hospital.MAX_LENGTH_DESCRIPTION)
    private String name;

	private Double lon;
	private Double lat;

    public Hospital() {};
    
    public Hospital(String string, String description) {
		this.name = string;
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

	public Double getLon() {
		return lon;
	}

	public Double getLat() {
		return lat;
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
	
	public Hospital withDescription(String description) {
		this.description = description;
		return this;
	}

	public Hospital withName(String name) {
		this.name = name;
		return this;
	}

	public Hospital withLon(Double lon) {
		this.lon = lon;
		return this;
	}

	public Hospital withLat(Double lat) {
		this.lat = lat;
		return this;
	}



 
}