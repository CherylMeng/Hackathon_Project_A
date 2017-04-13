package oracle.model;

import java.util.ArrayList;
import java.util.HashMap;

import oracle.db.DBConnection;

public class OfficeDepotService {
    
    private static CatalogTree tree = CatalogService.getTree();
    
    private static HashMap<String, Long> currencyMap = DBConnection.getCurrencyMap();
    
    public ArrayList<OfficeDepotInfo> getOfficeDepotByCatalog(long catalogID){
        ArrayList<OfficeDepot> officeDeporList = DBConnection.getOfficeDepotByCatalog(catalogID);
        return populateOfficeDepotInfo(officeDeporList);
    }
    
    public ArrayList<OfficeDepotInfo> getOfficeDepotBySupplier(long supplierID){
        ArrayList<OfficeDepot> officeDeporList = DBConnection.getOfficeDepotBySupplier(supplierID);
        return populateOfficeDepotInfo(officeDeporList);
    }


    public static ArrayList<OfficeDepotInfo> getOfficeDepotList(){
        return DBConnection.getOfficeDepotList();
    }

    public static boolean  addOfficeDepot(int id, String title, Double price, String pricurl){
        return DBConnection.addOfficeDepot(id, title, price, pricurl);
    }

    public static boolean addScreenshot(int id, String picurl){
        return DBConnection.addScreenshot(id, picurl);
    }

    public static ArrayList<String> getScreenshot(int id){
        return DBConnection.getScreenshot(id);

    }
    public ArrayList<OfficeDepotInfo> getAllOfficeDepot(){
        ArrayList<OfficeDepot> officeDeporList = DBConnection.getAllOfficeDepot();
        return populateOfficeDepotInfo(officeDeporList);
    }
    
    public HashMap<String, ArrayList<SKU>> getOfficeDepotDetails(long officeDepotID){
        HashMap<String, ArrayList<SKU>> map = new HashMap<String, ArrayList<SKU>>();
        map.put("SKU", DBConnection.getSkuByOfficeDepot(officeDepotID));
        return map;
    }
    
    public boolean deleteOfficeDepot(long officeDepotID){
        return DBConnection.deleteOfficeDepot(officeDepotID);
    }
    
    public OfficeDepotForm getAddOfficeDepotFillingForm(){
        OfficeDepotForm form = new OfficeDepotForm();
        form.setCatalogTree(tree);
        
        ArrayList<SKU> skuList = DBConnection.getSKUType();
        
        ArrayList<FormElement> mandatorySKU = new ArrayList<FormElement>();
        ArrayList<FormElement> optionalSKU = new ArrayList<FormElement>();
        
        for (SKU sku : skuList) {
            FormElement formElement = new FormElement();
            formElement.setLabelDisplayName(sku.getSkuName());
            formElement.setDefaultValue("");
            formElement.setAdditionalInfo(Long.toString(sku.getSkuType()));
            formElement.setIsText(true);
            if(sku.getSkuID() == 3) {
                formElement.setSelectMap(currencyMap);
            }
            if (sku.isMandatory()) {
                mandatorySKU.add(formElement);
            } else {
                optionalSKU.add(formElement);
            }
        }
        form.setMandatorySKU(mandatorySKU);
        form.setOptionalSKU(optionalSKU);
        return form;
    }
    
    public long saveOfficeDepot(long catalogID, long supplierID, double priceValue, long currencyID, ArrayList<SKU> skuList){
        OfficeDepot officeDepot = new OfficeDepot();
        officeDepot.setCatalogID(catalogID);
        officeDepot.setSupplierID(supplierID);
        officeDepot.setSkuList(skuList);
        
        Price price = new Price();
        price.setPriceValue(priceValue);
        price.setCurrencyID(currencyID);
        
        officeDepot.setPrice(price);
        
        return DBConnection.addOfficeDepot(officeDepot);
    }
    
    public OfficeDepotForm getUpdateOfficeDepotFillingForm(){
        OfficeDepotForm form = new OfficeDepotForm();
        
        ArrayList<SKU> skuList = DBConnection.getSKUType();

        ArrayList<FormElement> mandatorySKU = new ArrayList<FormElement>();
        ArrayList<FormElement> optionalSKU = new ArrayList<FormElement>();
        
        for (SKU sku : skuList) {
            FormElement formElement = new FormElement();
            formElement.setLabelDisplayName(sku.getSkuName());
            formElement.setAdditionalInfo(Long.toString(sku.getSkuType()));
            formElement.setIsText(true);
            if(sku.getSkuID() == 3) {
                Price price = DBConnection.getPrice(sku.getSkuID());
                formElement.setDefaultValue(Double.toString(price.getPriceValue()));
                formElement.setSelectMap(currencyMap);
            } else {
                formElement.setDefaultValue(sku.getSkuValue());
            }
            
            if (sku.isMandatory()) {
                mandatorySKU.add(formElement);
            } else {
                optionalSKU.add(formElement);
            }
        }
        form.setMandatorySKU(mandatorySKU);
        form.setOptionalSKU(optionalSKU);
        return form;
    }
    
    public boolean updateOfficeDepot(ArrayList<SKU> skuList){
        OfficeDepot officeDepot = new OfficeDepot();
        officeDepot.setSkuList(skuList);
        return DBConnection.updateOfficeDepot(officeDepot);
    }
    
    private ArrayList<OfficeDepotInfo> populateOfficeDepotInfo(ArrayList<OfficeDepot> officeDeporList){
        ArrayList<OfficeDepotInfo> infoList = new ArrayList<OfficeDepotInfo>();
        for (OfficeDepot item : officeDeporList) {
            OfficeDepotInfo officeDepotInfo = new OfficeDepotInfo();
            officeDepotInfo.setOfficeDepotID(item.getOfficeDepotID());
            officeDepotInfo.setOfficeDepotPrice(item.getPrice().getPriceValue());
            officeDepotInfo.setCurrencySymbol(item.getPrice().getCurrencySymbol());
            officeDepotInfo.setCurencyID(item.getPrice().getCurrencyID());
            
            for (SKU sku : item.getSkuList()) {
                if (sku.getSkuID() == 15){
                    officeDepotInfo.setPicDir(sku.getSkuValue());
                }
                if (sku.getSkuID() == 2){
                    officeDepotInfo.setOfficeDepotTitle(sku.getSkuValue());
                }
            }
            infoList.add(officeDepotInfo);
        }
        return infoList;
    }
}
