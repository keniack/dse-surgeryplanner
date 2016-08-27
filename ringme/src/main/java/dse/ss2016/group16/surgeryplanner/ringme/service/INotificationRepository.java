package dse.ss2016.group16.surgeryplanner.ringme.service;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:20:20 PM
 *
 */
public interface INotificationRepository extends MongoRepository<Notification, String>{
	
	@Query("{ 'entityId' :  ?0 , 'type' : ?1 }")
	List<Notification> findByTypeAndEntityId(String entityId, String type);

}
