package oracle.model;

import java.util.ArrayList;


public class OrderInfo {
    
   private String totalPrice;
   
   private String justification;
   
   private String submitDate;
   
   private ArrayList<ActionType> actions;

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getJustification() {
        return justification;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setActions(ArrayList<ActionType> actions) {
        this.actions = actions;
    }

    public ArrayList<ActionType> getActions() {
        return actions;
    }
}
