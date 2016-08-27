package dse.ss2016.group16.surgeryplanner.core.entity;

import java.util.Date;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 09.06.2016.
 */
@Document
public class Notification {


    public static final int MAX_LENGTH_TEXT = 500;

    @Id
    private String id;
    //Manual Reference to either a Doctor, Hospital or Patient
    private String entityId;
    private String type;
    private Date date;
    @Size(max = Notification.MAX_LENGTH_TEXT)
    private String text;

    public Notification() {};
    
    public Notification(String entityId, NotificationType type, Date date, String text) {
        this.entityId = entityId;
        this.type = type.toString();
        this.date = date;
        this.text = text;
    }

	public void setType(NotificationType type) {
		if(type!=null) this.type = type.toString();
	}
    
    public String getEntityId() {return entityId;}

    public NotificationType getType() {
    	return NotificationType.parse(type);
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

	public void setId(String id) {
		this.id = id;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setText(String text) {
		this.text = text;
	}

}
