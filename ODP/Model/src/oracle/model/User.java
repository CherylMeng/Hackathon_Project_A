package oracle.model;

import java.util.ArrayList;

public class User {
    
    private long userID;
    
    private String password;
    
    private String role;
    
    private String username;
    
    private long managerID;
    
    private String status;
    
    private ArrayList<UserInfoItem> userInfo;
    
    public User(){
        this.userInfo = new ArrayList<UserInfoItem>();
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getUserID() {
        return userID;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setManagerID(long managerID) {
        this.managerID = managerID;
    }

    public long getManagerID() {
        return managerID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setUserInfo(ArrayList<UserInfoItem> userInfo) {
        this.userInfo = userInfo;
    }

    public ArrayList<UserInfoItem> getUserInfo() {
        return userInfo;
    }
    
    public void addUserInfoItem(UserInfoItem item) {
        userInfo.add(item);
    }
}
