package oracle.model;


public class Catalog {
    private long catalogID;
    
    private long parentCatalog;
    
    private int catalogDepth;
    
    private String catalogName;


    public void setCatalogID(long catalogID) {
        this.catalogID = catalogID;
    }

    public long getCatalogID() {
        return catalogID;
    }

    public void setParentCatalog(long parentCatalog) {
        this.parentCatalog = parentCatalog;
    }

    public long getParentCatalog() {
        return parentCatalog;
    }

    public void setCatalogDepth(int catalogDepth) {
        this.catalogDepth = catalogDepth;
    }

    public int getCatalogDepth() {
        return catalogDepth;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getCatalogName() {
        return catalogName;
    }
}
