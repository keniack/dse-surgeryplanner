package dse.ss2016.group16.surgeryplanner.ringme.service;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;
import dse.ss2016.group16.surgeryplanner.ringme.RingMeApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 09.06.2016.
 */
@Service
public class NotificationService {
	
	@Autowired
	private INotificationRepository respository;

    public Notification create(Notification notification) {
    	respository.save(notification);
        return notification;
    }

    public Notification findById(String id)  {
        Notification result = respository.findOne(id);
        return result;
    }

    public Notification delete(String id) {
        Notification deleted = findById(id);
        respository.delete(deleted);
        return deleted;
    }

    public List<Notification> findAll() {
        List<Notification> notifications = respository.findAll();
        return notifications;
    }

    public void deleteAll(){
    	respository.deleteAll();
    }


    public List<Notification> findByEntityId(String entityId, NotificationType type)  {

        Query query = new Query();
        query.addCriteria(Criteria.where("entityId").is(entityId));
        query.addCriteria(Criteria.where("type").is(type));
        return respository.findByTypeAndEntityId( entityId, type!=null?type.toString():""); 
    }
    
    public void delete() {
    	respository.deleteAll();
    }

}
