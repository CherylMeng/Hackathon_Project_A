package oracle.controller;

import oracle.engine.Context;
import oracle.engine.IService;

import oracle.model.OfficeDepotService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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
            }else{
               if( OfficeDepotService.addOfficeDepot(Integer.parseInt(price), name, Double.parseDouble(price), picture)) {
                   returnCode = 201;
                   returnMsg = "success";
               }else{
                   returnCode = 500;
                   returnMsg = "failed";
               }
            }
        }else if("addScreenshot".equals(operation)){
            String price = requestJSON.getString("price");
            String picture = requestJSON.getString("picture");
            if(OfficeDepotService.addScreenshot(Integer.parseInt(price), picture)){
                returnCode = 201;
                returnMsg = "success";
            }else{
                returnCode = 500;
                returnMsg = "failed";
            }
        }else if("getScreenshot".equals(operation)){
            String id = requestJSON.getString("id");
            returnData = new JSONArray();
            List<String> screenshots = OfficeDepotService.getScreenshot(Integer.parseInt(id));
            for(String screenshot : screenshots){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("screenshot", screenshot);
                returnData.put(jsonObject);
            }
            returnCode = 201;
            returnMsg = "success";
        }
        responseJSON.put("returnCode",returnCode);
        responseJSON.put("returnMsg", returnMsg);
        responseJSON.put("returnData", returnData);
        context.setJsonResponseObject(responseJSON);
    }
}
