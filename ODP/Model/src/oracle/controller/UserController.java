package oracle.controller;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject; 
import oracle.engine.Context;
import oracle.engine.IService; 

public class UserController implements IService {
	public void execute(Context context) throws JSONException {
		JSONObject requestJSON = context.getJsonRequestObject();
		JSONObject responseJSON = new JSONObject(); 
		String operation = requestJSON.getString("operation");
		int returnCode = 0;
		String returnMsg = null;
		JSONArray returnData = null;
		if("sayhello".equals(operation)){
			 returnCode = 201;
			 returnMsg = "Controller is available";
		}  
		responseJSON.put("returnCode",returnCode);
		responseJSON.put("returnMsg", returnMsg);
		responseJSON.put("returnData", returnData);
		context.setJsonResponseObject(responseJSON);
		}


}
