package oracle.controller;

import oracle.engine.Context;
import oracle.engine.IService;
import oracle.model.AccountService;
import oracle.model.OfficeDepot;
import oracle.model.OfficeDepotInfo;
import oracle.model.OfficeDepotService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiadai on 2017/4/13.
 */
public class OrderController implements IService {
    public void execute(Context context) throws JSONException {
        JSONObject requestJSON = context.getJsonRequestObject();
        JSONObject responseJSON = new JSONObject();
        String operation = requestJSON.getString("operation");
        int returnCode = 0;
        String returnMsg = null;
        JSONArray returnData = null;
        if("getItemList".equals(operation)){
            List<OfficeDepotInfo> officeDepots =mock();
            returnData = new JSONArray();
            for(OfficeDepotInfo officeDepotInfo: officeDepots){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ID", officeDepotInfo.getOfficeDepotID());
                jsonObject.put("TITLE", officeDepotInfo.getOfficeDepotTitle());
                jsonObject.put("IMAGE_URL", officeDepotInfo.getPicDir());
                jsonObject.put("PRICE", officeDepotInfo.getOfficeDepotPrice());
                returnData.put(jsonObject);
            }
            returnCode = 201;
            returnMsg ="successful";
        }
        responseJSON.put("returnCode",returnCode);
        responseJSON.put("returnMsg", returnMsg);
        responseJSON.put("returnData", returnData);
        context.setJsonResponseObject(responseJSON);
    }

    public List<OfficeDepotInfo> mock(){
        List<OfficeDepotInfo> officeDepots = new ArrayList<OfficeDepotInfo>();
        for(int i = 10; i<=50; i++){
            OfficeDepotInfo officeDepotInfo = new OfficeDepotInfo();
            officeDepotInfo.setOfficeDepotID(i);
            officeDepotInfo.setOfficeDepotTitle("ITEM_"+i);
            officeDepotInfo.setPicDir("images"+i+"_default.jpg");
            officeDepotInfo.setOfficeDepotPrice(i*10);
            officeDepots.add(officeDepotInfo);
        }
        return officeDepots;
    }
}
