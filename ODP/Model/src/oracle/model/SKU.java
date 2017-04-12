package oracle.model;


public class SKU {
    private long skuID;
    
    private long officeDepotID;
    
    private long skuType;
    
    private String skuName;
    
    private String skuValue;
    
    private boolean isMandatory;

    public void setSkuID(long skuID) {
        this.skuID = skuID;
    }

    public long getSkuID() {
        return skuID;
    }

    public void setOfficeDepotID(long officeDepotID) {
        this.officeDepotID = officeDepotID;
    }

    public long getOfficeDepotID() {
        return officeDepotID;
    }

    public void setSkuType(long skuType) {
        this.skuType = skuType;
    }

    public long getSkuType() {
        return skuType;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuValue(String skuValue) {
        this.skuValue = skuValue;
    }

    public String getSkuValue() {
        return skuValue;
    }


    public void setIsMandatory(boolean isMandatory) {
        this.isMandatory = isMandatory;
    }

    public boolean isMandatory() {
        return isMandatory;
    }
}
