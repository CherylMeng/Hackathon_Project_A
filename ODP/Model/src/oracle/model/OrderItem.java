package oracle.model;

import java.util.ArrayList;


public class OrderItem {
    
    private long orderID;
    
    private long officeDepotID;
    
    private int amount;
    
    private double discount = 1.0;
    
    private Price price;
    
    private ArrayList<SKU> skuList;


    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOfficeDepotID(long officeDepotID) {
        this.officeDepotID = officeDepotID;
    }

    public long getOfficeDepotID() {
        return officeDepotID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }


    public void setPrice(Price price) {
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }

    public void setSkuList(ArrayList<SKU> skuList) {
        this.skuList = skuList;
    }

    public ArrayList<SKU> getSkuList() {
        return skuList;
    }
}
