package oracle.model;

import java.util.ArrayList;

import oracle.db.DBConnection;

public class NotificationService {
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
    
    public long createNotification(Notification notification){
        return DBConnection.createNotification(notification);
    }
}
