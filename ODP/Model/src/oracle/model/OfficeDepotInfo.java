package oracle.model;


public class OfficeDepotInfo {
    
   private long officeDepotID;
   
   private double officeDepotPrice;
   
   private long curencyID;
   
   private String currencySymbol;
   
   private String picDir;

    public void setOfficeDepotID(long officeDepotID) {
        this.officeDepotID = officeDepotID;
    }

    public long getOfficeDepotID() {
        return officeDepotID;
    }

    public void setOfficeDepotPrice(double officeDepotPrice) {
        this.officeDepotPrice = officeDepotPrice;
    }

    public double getOfficeDepotPrice() {
        return officeDepotPrice;
    }

    public void setCurencyID(long curencyID) {
        this.curencyID = curencyID;
    }

    public long getCurencyID() {
        return curencyID;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setPicDir(String picDir) {
        this.picDir = picDir;
    }

    public String getPicDir() {
        return picDir;
    }
}
