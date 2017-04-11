package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import oracle.db.DBConnection;

import oracle.utils.DBConstant;

public class ToDoListService {
    
    private static HashMap<Long, String> actionMap = DBConnection.getActionTypeMap();
    
    private static HashMap<String, Long> statusMap = DBConnection.getInitialStatusMap();
    
    private static NotificationService ns = new NotificationService();
    
    private static HashMap<String, Long> reversedHashMap = null;
    
    static{
        reversedHashMap = new HashMap<String, Long>();
        Set<Long> keys = actionMap.keySet();
        for (Long key : keys) {
            reversedHashMap.put(actionMap.get(key), key);
        }
    }
    
    public ArrayList<ToDoListItem> getAccountToDoList(long assigneeID, String managementType){
        ArrayList<ToDoListItem> todoList = DBConnection.getToDoListByAssignee(assigneeID, DBConstant.MANAGEMENT_TYPE_ACCOUNT);
        for(ToDoListItem item : todoList) {
            long userID = item.getOwnerID();
            User user = DBConnection.getUser(userID, true, false);
            AccountInfo accountInfo = new AccountInfo();
            accountInfo.setAccount(user.getUsername());
            accountInfo.setStatus(user.getStatus());
            for (UserInfoItem userInfo : user.getUserInfo()){
                if ("First Name".equals(userInfo.getDisplayName())){
                    accountInfo.setFirstName(userInfo.getValue());
                } else if ("Last Name".equals(userInfo.getDisplayName())){
                    accountInfo.setLastName(userInfo.getValue());
                } else if ("Supplier Name".equals(userInfo.getDisplayName())){
                    accountInfo.setSupplierName(userInfo.getValue());
                }
            }
            ArrayList<ActionType> actions = DBConnection.getActions(item.getToDoListItemID(), statusMap.get(accountInfo.getStatus()));
            accountInfo.setActions(actions);
            item.setAccountInfo(accountInfo);
        }
        return todoList;
    }
    
    public ArrayList<ToDoListItem> getOrderToDoList(long assigneeID, String managementType){
        ArrayList<ToDoListItem> todoList = DBConnection.getToDoListByAssignee(assigneeID, DBConstant.MANAGEMENT_TYPE_ORDER);
        for(ToDoListItem item : todoList) {
            long userID = item.getOwnerID();
            Order order = DBConnection.getOrderById(item.getOrderID());
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setTotalPrice(order.getTotal().getCurrencySymbol() + " " +order.getTotal().getPriceValue());
            orderInfo.setJustification(order.getJustification());
            ArrayList<ActionType> actions = DBConnection.getActions(item.getToDoListItemID(), order.getOrderStatus());
            orderInfo.setActions(actions);
            item.setOrderInfo(orderInfo);
        }
        return todoList;
    }
    
    public long createToDoListForAccount(User user){
        return DBConnection.createToDoListForAccount(user);
    }
    
    public long createToDoListForOrder(Order order){
        return DBConnection.createToDoListForOrder(order);
    }
    
    public boolean closeToDoList(long todoListID, String action){
        ToDoListItem toDoList = DBConnection.getToDoListById(todoListID);
        if(toDoList.getOrderID() != -1 && toDoList.getOrderID() != 0){
            ns.createNotificationForOrder(toDoList.getOrderID(), toDoList.getOwnerID(), toDoList.getTypeID(), reversedHashMap.get(action));
        } else {
            User user = DBConnection.getUser(toDoList.getOwnerID(), true, false);
            ns.createNotificationForAccount(toDoList.getOwnerID(), user.getUsername(), toDoList.getTypeID(), reversedHashMap.get(action));
        }
        return DBConnection.closeToDoList(todoListID);
    }
}
