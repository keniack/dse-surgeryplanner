package dse.ss2016.group16.surgeryplanner.core.entity;

import org.springframework.data.annotation.Id;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
public class PatientLocation {


    @Id
    private String id;

    //reference to Hospital
    private String patientId;
    private double[] loc;

    public PatientLocation(String patientId, double[] loc) {
        this.patientId = patientId;
        this.loc = loc;
    }

    public String getPatientId() {
        return patientId;
    }

    public double[] getLoc() {
        return loc;
    }

    public String getId() {
        return id;
    }
}
