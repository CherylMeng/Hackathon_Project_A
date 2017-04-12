package oracle.model;


public class ActionType {
    private long actionID;
    
    private String actionName;
    
    private String message;
    
    private String requestUrl;

    public void setActionID(long actionID) {
        this.actionID = actionID;
    }

    public long getActionID() {
        return actionID;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }
}
