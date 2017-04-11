package oracle.model;

import java.util.ArrayList;

public class HomepageInfo {
    private long userID;
    
    private long roleID;
    
    private String userName;
    
    private String roleName;
    
    private int toDoListNum;
    
    private int notificationNum;
    
    private ArrayList<Notification> NotificationList;
    
    private ArrayList<ToDoListItem> todoList;

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setNotificationList(ArrayList<Notification> NotificationList) {
        this.NotificationList = NotificationList;
    }

    public ArrayList<Notification> getNotificationList() {
        return NotificationList;
    }

    public void setTodoListItem(ArrayList<ToDoListItem> todoListItem) {
        this.todoList = todoListItem;
    }

    public ArrayList<ToDoListItem> getTodoListItem() {
        return todoList;
    }

    public void setToDoListNum(int toDoListNum) {
        this.toDoListNum = toDoListNum;
    }

    public int getToDoListNum() {
        return toDoListNum;
    }

    public void setNotificationNum(int notificationNum) {
        this.notificationNum = notificationNum;
    }

    public int getNotificationNum() {
        return notificationNum;
    }

    public void setRoleID(long roleID) {
        this.roleID = roleID;
    }

    public long getRoleID() {
        return roleID;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
