package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    private long orderID;
    
    private long orderType;
    
    private String orderTypeName;
    
    private long orderStatus;
    
    private String orderStatusName;
    
    private long Requestor;
    
    private String RequestorName;
    
    private long Receiver;
    
    private String ReceiverName;
    
    private Price total;
    
    private String justification;
    
    private String createdTime;
    
    private String submittedTime;
    
    private String approvedTime;
    
    private String processingTime;
    
    private String completedTime;
    
    private String deletedTime;
    
    private String refusedTime;
    
    private String canceledTime;
    
    private ArrayList<OrderItem> orderItems;


    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderType(long orderType) {
        this.orderType = orderType;
    }

    public long getOrderType() {
        return orderType;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setOrderStatus(long orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setTotal(Price total) {
        this.total = total;
    }

    public Price getTotal() {
        return total;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setSubmittedTime(String submittedTime) {
        this.submittedTime = submittedTime;
    }

    public String getSubmittedTime() {
        return submittedTime;
    }

    public void setApprovedTime(String approvedTime) {
        this.approvedTime = approvedTime;
    }

    public String getApprovedTime() {
        return approvedTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }

    public String getProcessingTime() {
        return processingTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setDeletedTime(String deletedTime) {
        this.deletedTime = deletedTime;
    }

    public String getDeletedTime() {
        return deletedTime;
    }

    public void setRefusedTime(String refusedTime) {
        this.refusedTime = refusedTime;
    }

    public String getRefusedTime() {
        return refusedTime;
    }

    public void setCanceledTime(String canceledTime) {
        this.canceledTime = canceledTime;
    }

    public String getCanceledTime() {
        return canceledTime;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setRequestor(long Requestor) {
        this.Requestor = Requestor;
    }

    public long getRequestor() {
        return Requestor;
    }

    public void setReceiver(long Receiver) {
        this.Receiver = Receiver;
    }

    public long getReceiver() {
        return Receiver;
    }

    public void setRequestorName(String RequestorName) {
        this.RequestorName = RequestorName;
    }

    public String getRequestorName() {
        return RequestorName;
    }

    public void setReceiverName(String ReceiverName) {
        this.ReceiverName = ReceiverName;
    }

    public String getReceiverName() {
        return ReceiverName;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }

    public void calculateTotalPrice(HashMap<Long, Double> currencyMap){
        long targetCurrency = this.total.getCurrencyID();
        double totalPrice = 0.0;
        for (OrderItem item : this.orderItems){
            double initialExchange = currencyMap.get(item.getPrice().getCurrencyID());
            double targetExchange = currencyMap.get(targetCurrency);
            double exchangeRate = initialExchange / targetExchange;
            totalPrice += (item.getPrice().getPriceValue() * item.getAmount() * item.getDiscount()) * exchangeRate;
        }
        this.total.setPriceValue(totalPrice);
    }
}
