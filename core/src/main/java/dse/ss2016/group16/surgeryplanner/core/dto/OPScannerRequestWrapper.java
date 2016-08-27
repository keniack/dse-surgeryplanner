package dse.ss2016.group16.surgeryplanner.core.dto;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;
import dse.ss2016.group16.surgeryplanner.core.enums.OPDuration;
import dse.ss2016.group16.surgeryplanner.core.enums.OPType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 10.06.2016.
 */
public class OPScannerRequestWrapper implements Serializable{

    private List<OPSlot> freeSlots;
    private String patientId;
    private String doctorId;
    private String type;
    private Date begin;
    private Date end;
    private long duration;


    public OPScannerRequestWrapper() {};
    
    public OPScannerRequestWrapper(List<OPSlot> freeSlots, String patientId, String doctorId, Date begin, Date end, Integer duration, String type) {
        this.freeSlots = freeSlots;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.begin = begin;
        this.end = end;
        this.duration = duration;
        this.type = type;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    public List<OPSlot> getFreeSlots() {
        return freeSlots;
    }

    public String getPatientId() {
        return patientId;
    }

    public Date getBegin() {
        return begin;
    }

    public Date getEnd() {
        return end;
    }

    public long getDuration() {
        return duration;
    }

    public void setFreeSlots(List<OPSlot> freeSlots) {
        this.freeSlots = freeSlots;
    }

    public OPType getOPType() {
        return OPType.parse(type);
    }

    public String getType() {
        return type;
    }

    public JSONObject convertToJsonObject() {

        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("patientId", this.getPatientId());
            jsonObject.put("doctorId", this.getDoctorId());
            String begin = format.format(this.getBegin());
            String end = format.format(this.getEnd());
            jsonObject.put("begin", begin);
            jsonObject.put("end", end);
            jsonObject.put("duration", this.getDuration());
            jsonObject.put("type", this.getType());

            JSONArray freeSlotsJson = new JSONArray();

            for(OPSlot slot : this.getFreeSlots() ){
                JSONObject jsonSlot = new JSONObject();
                jsonSlot.put("type", slot.getType());
                jsonSlot.put("status", slot.getStatus());
                String startTime = format.format(slot.getStartTime());
                String endTime = format.format(slot.getEndTime());
                jsonSlot.put("startTime", startTime);
                jsonSlot.put("endTime", endTime);
                jsonSlot.put("doctorId", slot.getDoctorId());
                jsonSlot.put("patientId", slot.getPatientId());
                jsonSlot.put("hospitalId", slot.getHospitalId());

                freeSlotsJson.put(jsonSlot);
            }
            jsonObject.put("freeSlots", freeSlotsJson);
            return jsonObject;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;

    }

}
