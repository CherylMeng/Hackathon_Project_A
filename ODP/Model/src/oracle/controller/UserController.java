package oracle.controller;
import oracle.model.AccountService;
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
		if("login".equals(operation)){
			String userName = requestJSON.getString("userName");
			String password = requestJSON.getString("password");
			if(userName.length() ==0 ||userName == null || password.length() == 0 || password == null){
				returnCode = 404;
				returnMsg = "Username or password cannot be null.";
			}else{
				AccountService accountService = new AccountService();
				boolean isExist = accountService.login(userName, password);
				if(isExist){
					returnCode = 201;
					returnMsg = "login successfully";
				}else{
					returnCode = 404;
					returnMsg = "username or password is invalid.";
				}
			}
		}  
		responseJSON.put("returnCode",returnCode);
		responseJSON.put("returnMsg", returnMsg);
		responseJSON.put("returnData", returnData);
		context.setJsonResponseObject(responseJSON);
		}


}
