package oracle.model;


public class Notification {
    private long notificationID;
    
    private long actionMappingID;
    
    private long receiverID;
    
    private String message;
    
    private long orderID;
    
    private String username;
    
    private String createdTime;

    public void setNotificationID(long notificationID) {
        this.notificationID = notificationID;
    }

    public long getNotificationID() {
        return notificationID;
    }

    public void setActionMappingID(long actionMappingID) {
        this.actionMappingID = actionMappingID;
    }

    public long getActionMappingID() {
        return actionMappingID;
    }

    public void setReceiverID(long receiverID) {
        this.receiverID = receiverID;
    }

    public long getReceiverID() {
        return receiverID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }
}
