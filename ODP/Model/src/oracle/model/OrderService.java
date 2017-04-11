package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import oracle.db.DBConnection;

import oracle.utils.DBConstant;

public class OrderService {
    
    private static ToDoListService todoListService = new ToDoListService();
    
    private static NotificationService notificationService = new NotificationService();
    
    private static HashMap<String, String> endingStateMap = DBConnection.getEndingStatusMap();
    
    private static HashMap<String, Long> currrencyMap = DBConnection.getCurrencyMap();
    
    private static HashMap<String, Long> orderTypeMap = DBConnection.getOrderTypeMap();
    
    private static HashMap<Long, String> actionMap = DBConnection.getActionTypeMap();
    
    private static HashMap<String, Long> reversedHashMap = null;
    
    static{
        reversedHashMap = new HashMap<String, Long>();
        Set<Long> keys = actionMap.keySet();
        for (Long key : keys) {
            reversedHashMap.put(actionMap.get(key), key);
        }
    }
    
    public ArrayList<FormElement> showOrderDetails(long orderID){
        ArrayList<FormElement> orderDetails = new ArrayList<FormElement>();
        Order order = DBConnection.getOrderById(orderID);
        
        FormElement orderIDElement = new FormElement();
        orderIDElement.setLabelDisplayName("OrderID");
        orderIDElement.setDefaultValue(Long.toString(orderID));
        orderDetails.add(orderIDElement);
        
        FormElement orderTypeElement = new FormElement();
        orderTypeElement.setLabelDisplayName("Order Type");
        orderIDElement.setDefaultValue(order.getOrderTypeName());
        orderDetails.add(orderTypeElement);
        
        FormElement orderTotalElement = new FormElement();
        orderTotalElement.setLabelDisplayName("Total Price");
        orderTotalElement.setDefaultValue(order.getTotal().getCurrencySymbol() + " " + order.getTotal().getPriceValue());
        orderDetails.add(orderTotalElement);
        
        FormElement orderStatusElement = new FormElement();
        orderStatusElement.setLabelDisplayName("Order Status");
        orderStatusElement.setDefaultValue(order.getOrderStatusName());
        orderDetails.add(orderStatusElement);
        
        FormElement createTimeElement = new FormElement();
        createTimeElement.setLabelDisplayName("Create Time");
        createTimeElement.setDefaultValue(order.getCreatedTime());
        orderDetails.add(createTimeElement);
        
        FormElement submitTimeElement = new FormElement();
        submitTimeElement.setLabelDisplayName("Submit Time");
        submitTimeElement.setDefaultValue(order.getSubmittedTime());
        orderDetails.add(submitTimeElement);
        
        FormElement approveTimeElement = new FormElement();
        approveTimeElement.setLabelDisplayName("Approve Time");
        approveTimeElement.setDefaultValue(order.getApprovedTime());
        orderDetails.add(approveTimeElement);
        
        FormElement processTimeElement = new FormElement();
        processTimeElement.setLabelDisplayName("Process Time");
        processTimeElement.setDefaultValue(order.getProcessingTime());
        orderDetails.add(processTimeElement);
        
        FormElement completeTimeElement = new FormElement();
        completeTimeElement.setLabelDisplayName("Complete Time");
        completeTimeElement.setDefaultValue(order.getCompletedTime());
        orderDetails.add(completeTimeElement);
        
        String orderItemsStr = "";
        for (OrderItem item : order.getOrderItems()){
            ArrayList<SKU> skuList = item.getSkuList();
            String brand = null, name = null;
            for(SKU sku : skuList){
                if (sku.getSkuID() == 1) {
                    brand = sku.getSkuValue();
                } else if (sku.getSkuID() == 2) {
                    name = sku.getSkuValue();
                }
            }
            if (brand != null && name != null){
                orderItemsStr += brand + " " + name + " * " + item.getAmount() + ", ";
            }
        }
        
        FormElement itemsElement = new FormElement();
        itemsElement.setLabelDisplayName("Order Items");
        itemsElement.setDefaultValue(orderItemsStr);
        orderDetails.add(itemsElement);
        
        return orderDetails;
    }
    
    public ArrayList<OrderInfo> getAllOrders(long userID){
        ArrayList<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
        ArrayList<Order> orders = DBConnection.getOrders(userID, DBConstant.ORDER_ROLE_REQUESTOR);
        for (Order item : orders){
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setTotalPrice(item.getTotal().getCurrencySymbol() + " " +item.getTotal().getPriceValue());
            orderInfo.setJustification(item.getJustification());
            long todoListTypeID = DBConnection.getToDoListType(DBConstant.USER_ROLE_REQUESTOR, DBConstant.USER_ROLE_REQUESTOR, DBConstant.MANAGEMENT_TYPE_ORDER);
            ArrayList<ActionType> actions = DBConnection.getActions(todoListTypeID, item.getOrderStatus());
            orderInfo.setActions(actions);
            orderInfoList.add(orderInfo);
        }
        return orderInfoList;
    }
    
    public boolean updateOrderStatus(long orderID, String action){
        boolean success = DBConnection.updateOrderStatus(orderID, endingStateMap.get(action));
        Order order = DBConnection.getOrderById(orderID);
        long toDoListTypeID = -1;
        if(DBConstant.ORDER_STATUS_SUBMITTED.equals(order.getOrderStatusName()) || DBConstant.ORDER_STATUS_APPROVED.equals(order.getOrderStatusName())) {
            toDoListTypeID = todoListService.createToDoListForOrder(order);
        }
        long receiverID = -1;
        notificationService.createNotificationForOrder(orderID, order.getRequestor(), toDoListTypeID, reversedHashMap.get(action));
        return success;
    }
    
    public HashMap<String, HashMap<String, Long>> checkout(){
        HashMap<String, HashMap<String, Long>> maps = new HashMap<String, HashMap<String, Long>>();
        maps.put("orderType", orderTypeMap);
        maps.put("currency", currrencyMap);
        return maps;
    }
    
    public ArrayList<FormElement> createOrder(Order order){
        long orderID = DBConnection.addOrder(order);
        return showOrderDetails(orderID);
    }
}
