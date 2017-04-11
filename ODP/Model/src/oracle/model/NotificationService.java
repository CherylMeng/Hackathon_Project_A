package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.db.DBConnection;

public class NotificationService {
    
    private static HashMap<String, Long> actionMap = DBConnection.getInitialStatusMap();
    
    public ArrayList<Notification> getUnreadNotification(long userID){
        return DBConnection.getNotification(userID, true);
    }
    
    public boolean updateNotification(ArrayList<Long> notificationIDs){
        boolean success = true;
        for (Long id : notificationIDs) {
            success &= DBConnection.closeNotification(id);
        }
        return success;
    }
    
    public long createNotificationForAccount(long userID, String username, long toDoListTypeID, long actionID){
        Notification notification = DBConnection.getActionMappedNotification(toDoListTypeID, actionID);
        notification.setReceiverID(userID);
        notification.setUsername(username);
        String filledMessage = notification.getMessage().replaceAll("<ACCOUNT>", username);
        notification.setMessage(filledMessage);
        
        return DBConnection.createNotification(notification);
    }
    
    public long createNotificationForOrder(long orderID, long receiverID, long toDoListTypeID, long actionID){
        Notification notification = DBConnection.getActionMappedNotification(toDoListTypeID, actionID);
        notification.setOrderID(orderID);
        notification.setReceiverID(receiverID);
        String filledMessage = notification.getMessage().replaceAll("<ORDER>", Long.toString(orderID));
        notification.setMessage(filledMessage);
        
        return DBConnection.createNotification(notification);
    }
}
