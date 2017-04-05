package oracle.engine;

import org.apache.log4j.Logger;
import org.json.JSONException;


public class ServiceNameRequestProcessor implements IRequestProcessor {
	private static Logger logger = Logger.getLogger(ServiceNameRequestProcessor.class);
	
	public void processContext(Context context) {
		try {
			String serviceName = context.getJsonRequestObject().getString("serviceName");
			if(serviceName != null){
				context.setServiceName(serviceName);
			}
			String operation = context.getJsonRequestObject().getString("operation");
			if(operation != null){
				context.setOperation(operation);
			}
		} catch (JSONException e) {
			logger.error(e.getMessage());
		}
		
	}

}
