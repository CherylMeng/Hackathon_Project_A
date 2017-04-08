package oracle.model;


public class ToDoListItem {
    
    private long toDoListItemID;
    
    private long typeID;
    
    private long ownerID;
    
    private String ownerName;
    
    private long assigneeID;
    
    private long orderID;
    
    private AccountInfo accountInfo;
    
    private OrderInfo orderInfo;

    public void setToDoListItemID(long toDoListItemID) {
        this.toDoListItemID = toDoListItemID;
    }

    public long getToDoListItemID() {
        return toDoListItemID;
    }

    public void setTypeID(long typeID) {
        this.typeID = typeID;
    }

    public long getTypeID() {
        return typeID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setAssigneeID(long assigneeID) {
        this.assigneeID = assigneeID;
    }

    public long getAssigneeID() {
        return assigneeID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }
}
