package oracle.model;


public class Price {
    private long priceID;
    
    private double priceValue;
    
    private long currencyID;
    
    private String currencyName;
    
    private String currencySymbol;


    public void setPriceID(long priceID) {
        this.priceID = priceID;
    }

    public long getPriceID() {
        return priceID;
    }

    public void setPriceValue(double priceValue) {
        this.priceValue = priceValue;
    }

    public double getPriceValue() {
        return priceValue;
    }

    public void setCurrencyID(long currencyID) {
        this.currencyID = currencyID;
    }

    public long getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }
}
