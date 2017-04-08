package oracle.model;

import java.util.ArrayList;

public class OfficeDepotForm {
    
    private CatalogTree catalogTree;
    
    private ArrayList<FormElement> mandatorySKU;
    
    private ArrayList<FormElement> optionalSKU;

    public void setCatalogTree(CatalogTree catalogTree) {
        this.catalogTree = catalogTree;
    }

    public CatalogTree getCatalogTree() {
        return catalogTree;
    }

    public void setMandatorySKU(ArrayList<FormElement> mandatorySKU) {
        this.mandatorySKU = mandatorySKU;
    }

    public ArrayList<FormElement> getMandatorySKU() {
        return mandatorySKU;
    }

    public void setOptionalSKU(ArrayList<FormElement> optionalSKU) {
        this.optionalSKU = optionalSKU;
    }

    public ArrayList<FormElement> getOptionalSKU() {
        return optionalSKU;
    }
}
