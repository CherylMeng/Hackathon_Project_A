package oracle.model;


public class UserInfoItem {
    
    private long infoItemID;
    
    private long type;
    
    private String typeName;
    
    private String displayName;
    
    private String value;


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
}
