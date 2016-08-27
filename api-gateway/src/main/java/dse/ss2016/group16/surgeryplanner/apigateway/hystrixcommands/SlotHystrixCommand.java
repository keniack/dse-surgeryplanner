package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONException;
import org.json.JSONObject;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 * Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on
 * 6:30:48 PM
 *
 */
public abstract class SlotHystrixCommand extends GenericHystrixCommand {

	OPSlot slot;

	public SlotHystrixCommand(String group, String askKey, OPSlot slot, String url) {
		super(group, askKey);
		this.slot = slot;
		setUrl(url);
	}

	public JSONObject convertObjectToJsonObject(OPSlot slot) {

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", slot.getType());
			jsonObject.put("status", slot.getStatus());
			String startTime = format.format(slot.getStartTime());
			String endTime = format.format(slot.getEndTime());
			jsonObject.put("startTime", startTime);
			jsonObject.put("endTime", endTime);
			jsonObject.put("doctorId", slot.getDoctorId());
			jsonObject.put("patientId", slot.getPatientId());
			jsonObject.put("hospitalId", slot.getHospitalId());
			return jsonObject;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

	public JSONObject convertOPSlotIdToJsonObject(OPSlot slot) {

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id", slot.getId());
			return jsonObject;

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return null;

	}

}
