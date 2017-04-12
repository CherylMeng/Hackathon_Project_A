package oracle.model;

import java.util.HashMap;

import oracle.db.DBConnection;

import oracle.utils.DBConstant;


public class UserInfoItem {
    
    private long infoItemID;
    
    private long type;
    
    private String typeName;
    
    private String displayName;
    
    private String value;
    
    private boolean isText;
    
    private String map;


    public void setInfoItemID(long infoItemID) {
        this.infoItemID = infoItemID;
    }

    public long getInfoItemID() {
        return infoItemID;
    }

    public void setType(long type) {
        this.type = type;
    }

    public long getType() {
        return type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

    public boolean isText() {
        return isText;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return map;
    }
    
    public HashMap<String, Long> constructSelectMap(){
        HashMap<String, Long> selectMap = new HashMap<String, Long>();
        if (map != null){
            if (DBConstant.SITE_MAP.equals(map)){
                selectMap = DBConnection.getSiteMap();
            } else if (DBConstant.MANAGER_MAP.equals(map)) {
                selectMap = DBConnection.getManagerMap();
            } else if (DBConstant.CURRENCY_MAP.equals(map)) {
                
            } else if (DBConstant.ORDER_TYPE_MAP.equals(map)) {
                selectMap = DBConnection.getOrderTypeMap();
            }
        }
        return selectMap;
    }
}
