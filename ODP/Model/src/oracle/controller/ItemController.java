package oracle.controller;

import oracle.engine.Context;
import oracle.engine.IService;
import oracle.model.AccountService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xiadai on 2017/4/13.
 */
public class ItemController implements IService {
    public void execute(Context context) throws JSONException {
        JSONObject requestJSON = context.getJsonRequestObject();
        JSONObject responseJSON = new JSONObject();
        String operation = requestJSON.getString("operation");
        int returnCode = 0;
        String returnMsg = null;
        JSONArray returnData = null;
        if("addItem".equals(operation)){
            String name = requestJSON.getString("name");
            String price = requestJSON.getString("price");
            String picture = requestJSON.getString("picture");
            if(name == null || name.length()<=0 || price == null || price.length()<=0){
                returnCode = 404;
                returnMsg = "Info cannot be null";
            }
            System.out.print(picture);
        }
        responseJSON.put("returnCode",returnCode);
        responseJSON.put("returnMsg", returnMsg);
        responseJSON.put("returnData", returnData);
        context.setJsonResponseObject(responseJSON);
    }
}
