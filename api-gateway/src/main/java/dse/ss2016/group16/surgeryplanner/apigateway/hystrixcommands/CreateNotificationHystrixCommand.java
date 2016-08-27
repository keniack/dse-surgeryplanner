package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import org.json.JSONObject;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
public class CreateNotificationHystrixCommand extends GenericHystrixCommand {

    private Notification notification;

    public CreateNotificationHystrixCommand(String group, String askkey, Notification notification){
        super(group,askkey);
        this.notification = notification;
        setUrl(PropertiesUtil.getValue("ringme.url.create"));
    }

    @Override
    protected String run() throws Exception {

        JSONObject jsonNotification = new JSONObject();
        jsonNotification.put("date", format.format(notification.getDate()));
        jsonNotification.put("entityId", notification.getEntityId());
        jsonNotification.put("type", notification.getType());
        jsonNotification.put("text", notification.getText());

        try {
            JSONObject obj = callApiPostMethod(ringmeUrl, url, jsonNotification);
            return obj.toString();
        } catch (Throwable e) {
            throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
        }
    }

    @Override
    protected String getFallback() {
        return ringmeUrl+url+" unavailable. Please contact system administrator";
    }

}
