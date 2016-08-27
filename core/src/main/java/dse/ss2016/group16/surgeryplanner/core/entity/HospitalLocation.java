package dse.ss2016.group16.surgeryplanner.core.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 10.06.2016.
 * The HospitalLocation of a Hospital, saved in OP-Scanner
 */
@Document
public class HospitalLocation {

    @Id
    private String id;

    //reference to Hospital
    private String hospitalId;
    private double[] loc;

    public HospitalLocation(String hospitalId, double[] loc) {
        this.hospitalId = hospitalId;
        this.loc = loc;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public double[] getLoc() {
        return loc;
    }

    public String getId() {
        return id;
    }

}
