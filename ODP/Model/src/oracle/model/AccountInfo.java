package oracle.model;

import java.util.ArrayList;


public class AccountInfo {
   private String account;
   
   private String firstName;
   
   private String lastName;
   
   private String supplierName;
   
   private String status;
   
   private ArrayList<ActionType> actions;

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return account;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setActions(ArrayList<ActionType> actions) {
        this.actions = actions;
    }

    public ArrayList<ActionType> getActions() {
        return actions;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }
}
