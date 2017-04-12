package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.db.DBConnection;

import oracle.utils.DBConstant;

public class AccountService {
    
    private static ToDoListService todoListService = new ToDoListService();
    
    private static HashMap<String, String> endingStateMap = DBConnection.getEndingStatusMap();
    
    private static HashMap<String, Long> statusMap = DBConnection.getInitialStatusMap();
    
    public boolean login(String userName, String password){
        return DBConnection.userAuth(userName, password);
    }
    
    public HashMap getRegisterRoles() {
        return DBConnection.getRoleMap(true);
    }
    
    public long createUser(long roleID){
        return DBConnection.createUser(roleID);
    }
    
    public boolean isUserExist(String username) {
        return DBConnection.isUserExist(username);
    }
    
    public ArrayList<FormElement> getRegisterBasicInfoForm(long userID) {
        ArrayList<FormElement> formElements = new ArrayList<FormElement>();
        User user = DBConnection.getUser(userID, true, true);
        String[] displayNames = {"Account", "Password", "Confirm"};
        for (String displayName : displayNames) {
            FormElement element = new FormElement();
            element.setLabelDisplayName(displayName);
            element.setIsText(true);
            element.setDefaultValue("");
            element.setSelectMap(null);
            formElements.add(element);
        }
        for (UserInfoItem info : user.getUserInfo()) {
            FormElement element = new FormElement();
            element.setLabelDisplayName(info.getDisplayName());
            element.setIsText(info.isText());
            element.setDefaultValue("");
            element.setSelectMap(info.getMap() == null ? null : info.constructSelectMap());
            formElements.add(element);
        }
        return formElements;
    }
    
    public boolean saveUser(User user){
        return DBConnection.saveUser(user);
    }
    
    public ArrayList<FormElement> getConfirmInfo(long userID){
        ArrayList<FormElement> formElements = new ArrayList<FormElement>();
        User user = DBConnection.getUser(userID, true, false);
        String firstName = null, lastName = null;
        for (UserInfoItem info : user.getUserInfo()) {
            if ("First Name".equals(info.getDisplayName())){
                firstName = info.getValue();
                continue;
            } else if ("Last Name".equals(info.getDisplayName())) {
                lastName = info.getValue();
                continue;
            }
            
            FormElement element = new FormElement();
            element.setLabelDisplayName(info.getDisplayName());
            element.setDefaultValue(info.getValue());
            formElements.add(element);
        }
        
        if (firstName != null && lastName != null) {
            FormElement element = new FormElement();
            element.setLabelDisplayName("Name");
            element.setDefaultValue(firstName + " " + lastName);
            formElements.add(0, element);
        }

        return formElements;
    }
    
    public boolean submitAccount(long userID){
        return DBConnection.updateUserStatus(userID, DBConstant.USER_STATUS_SUBMITTED);
    }
    
    public HomepageInfo showHomePage(long userID){
        HomepageInfo homepage = new HomepageInfo();
        User user = DBConnection.getUser(userID, true, false);
        int toDoListNum = DBConnection.getToDoListNum(userID);
        int notificationNum = DBConnection.getNotificationNum(userID);
        homepage.setUserID(userID);
        
        String username = null;
        if (DBConstant.USER_ROLE_SUPPLIER.equals(user.getRole())){
            for (UserInfoItem item : user.getUserInfo()){
                if("Supplier Name".equals(item.getDisplayName())){
                    username = item.getValue();
                    break;
                }
            }
        } else if (DBConstant.USER_ROLE_REQUESTOR.equals(user.getRole()) || DBConstant.USER_ROLE_MANAGER.equals(user.getRole())){
            String firstName = null, lastName = null;
            for (UserInfoItem item : user.getUserInfo()){
                if("First Name".equals(item.getDisplayName())){
                    firstName = item.getValue();
                } else if("Last Name".equals(item.getDisplayName())){
                    lastName = item.getValue();
                }
            }
            if (firstName != null && lastName != null) {
                username = firstName + " " + lastName;
            }
        }
        if (username == null) {
            username = user.getUsername();
        }
        
        homepage.setUserName(username);
        
        HashMap<String, Long> roleMap = DBConnection.getRoleMap(false);
        homepage.setRoleName(user.getRole());
        homepage.setRoleID(roleMap.get(user.getRole()));
        homepage.setNotificationNum(DBConnection.getNotificationNum(user.getUserID()));
        homepage.setToDoListNum(DBConnection.getToDoListNum(user.getUserID()));
        
        return homepage;
    }
    
    public HashMap<String, ArrayList<ToDoListItem>> checkToDoList(long userID, String roleName){
        HashMap<String, ArrayList<ToDoListItem>> todoList = new HashMap<String, ArrayList<ToDoListItem>>();
        if (DBConstant.USER_ROLE_MANAGER.equals(roleName)){
            ArrayList<ToDoListItem> accountToDoList = todoListService.getAccountToDoList(userID, DBConstant.MANAGEMENT_TYPE_ACCOUNT);
            ArrayList<ToDoListItem> orderToDoList = todoListService.getAccountToDoList(userID, DBConstant.MANAGEMENT_TYPE_ORDER);
            todoList.put("Account To-Do", accountToDoList);
            todoList.put("Order To-Do", orderToDoList);
        } else if (DBConstant.USER_ROLE_ADMIN.equals(roleName)) {
            ArrayList<ToDoListItem> accountToDoList = todoListService.getAccountToDoList(userID, DBConstant.MANAGEMENT_TYPE_ACCOUNT);
            todoList.put("Account To-Do", accountToDoList);
        } else if (DBConstant.USER_ROLE_SUPPLIER.equals(roleName)) {
            ArrayList<ToDoListItem> orderToDoList = todoListService.getAccountToDoList(userID, DBConstant.MANAGEMENT_TYPE_ORDER);
            todoList.put("Order To-Do", orderToDoList);
        }
        
        return todoList;
    }
    
    public boolean updateAccountStatus(long userID, String action){
        return DBConnection.updateUserStatus(userID, endingStateMap.get(action));
    }
    
    public ArrayList<AccountInfo> getAllAccounts(String role){
        ArrayList<User> users = DBConnection.getUserByRole(DBConstant.USER_ROLE_SUPPLIER);
        return populateAccountInfo(users);
    }
    
    public ArrayList<AccountInfo> getAllAccounts(long managerID){
        ArrayList<User> users = DBConnection.getUserByManager(managerID);
        return populateAccountInfo(users);
    }
    
    private ArrayList<AccountInfo> populateAccountInfo(ArrayList<User> users) {
        ArrayList<AccountInfo> accounts = new ArrayList<AccountInfo>();
        for (User user : users){
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
            long todoListTypeID = DBConnection.getToDoListType(DBConstant.USER_ROLE_MANAGER, user.getRole(), DBConstant.MANAGEMENT_TYPE_ACCOUNT);
            ArrayList<ActionType> actions = DBConnection.getActions(todoListTypeID, statusMap.get(accountInfo.getStatus()));
            accountInfo.setActions(actions);
            accounts.add(accountInfo);
        }
        return accounts;
    }
    
    public static void main(String[] args) {
        AccountService as = new AccountService();
        System.out.println(as.getRegisterRoles());
    }
}
