package dse.ss2016.group16.surgeryplanner.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * Aus irgendeinem mehr nicht erklärbarem Grund hat maven aufgehört zu builden
 * und möchte unbedingt eine Main Klasse, obwohl es vorher
 * hunderte Male ohne funktioniert hat...
 */
@SpringBootApplication
public class DummyMain {

    public static void main(String[] args){

        SpringApplication.run(DummyMain.class, args);
    }
}