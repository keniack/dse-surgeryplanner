package dse.ss2016.group16.surgeryplanner.apigateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.APIGatewayController;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.BookingApiController;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.MasterdataApiController;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.OPScannerApiController;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.RingMeApiController;
import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;

@SpringBootApplication
@EnableHystrixDashboard
@EnableCircuitBreaker
public class APIGateWayApp implements CommandLineRunner{
	
	public static void main(String[] args) {
        SpringApplication.run(APIGateWayApp.class, args);
        	
    }
	

	@Override
	public void run(String... arg0) throws Exception {
		try {
		
			while(true){
		       	APIGatewayController controler = new APIGatewayController();
		       	controler.getServiceStatus();
		       	BookingApiController bookingApi = new BookingApiController();
		       	bookingApi.findAll();
		       	MasterdataApiController masterController = new MasterdataApiController();
		       	masterController.getAllDoctors();
		       	masterController.getAllHospitals();
		       	masterController.getAllPatitents();
		       	
		       	RingMeApiController ringMe = new RingMeApiController();
		       	ringMe.findNotificationByDoctor("asd");
		       	
		       	OPScannerApiController opController = new OPScannerApiController();
		       	opController.findAndBookFreeSlot(new OPScannerRequestWrapper());
						Thread.sleep(5000);
		       }
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	

}
