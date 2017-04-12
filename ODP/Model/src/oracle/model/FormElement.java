package oracle.model;

import java.util.HashMap;

public class FormElement {
    
    private String labelDisplayName;
    
    private boolean isText;
    
    private String defaultValue;
    
    private HashMap<String, Long> selectMap;
    
    //Used in SKU for storing skuTypeID
    private String additionalInfo;


    public void setLabelDisplayName(String labelDisplayName) {
        this.labelDisplayName = labelDisplayName;
    }

    public String getLabelDisplayName() {
        return labelDisplayName;
    }

    public void setIsText(boolean isText) {
        this.isText = isText;
    }

    public boolean isIsText() {
        return isText;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setSelectMap(HashMap<String, Long> selectMap) {
        this.selectMap = selectMap;
    }

    public HashMap<String, Long> getSelectMap() {
        return selectMap;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
