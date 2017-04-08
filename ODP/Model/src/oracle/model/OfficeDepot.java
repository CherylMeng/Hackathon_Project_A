package oracle.model;

import java.util.ArrayList;

public class OfficeDepot {
    private long officeDepotID;
    
    private long catalogID;
    
    private long supplierID;
    
    private String name;
    
    private Price price;
    
    private ArrayList<SKU> skuList;

    public void setOfficeDepotID(long officeDepotID) {
        this.officeDepotID = officeDepotID;
    }

    public long getOfficeDepotID() {
        return officeDepotID;
    }

    public void setCatalogID(long catalogID) {
        this.catalogID = catalogID;
    }

    public long getCatalogID() {
        return catalogID;
    }

    public void setSupplierID(long supplierID) {
        this.supplierID = supplierID;
    }

    public long getSupplierID() {
        return supplierID;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
