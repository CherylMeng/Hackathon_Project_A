package oracle.model;

import java.util.ArrayList;

import oracle.db.DBConnection;

public class CatalogService {
    
    private static CatalogTree tree = new CatalogTree();
    
    static{
        buildCataLogTree(tree, 0);
    }
    
    public static Catalog getCatalog(long catalogID){
        return DBConnection.getCataLog(catalogID);
    }
    
    public static ArrayList<Catalog> getCatalogByParent(long parentID) {
        return DBConnection.getCataLogByParent(parentID);
    }
    
    private static void buildCataLogTree(CatalogTree catalogTree, long rootID){
        Catalog root = getCatalog(rootID);
        ArrayList<Catalog> subCatalogs = getCatalogByParent(rootID);
        if (root == null || subCatalogs == null || subCatalogs.size() == 0) return;
        catalogTree.setRoot(root);
        catalogTree.setSubCatalog(new ArrayList<CatalogTree>());
        for (Catalog subCatalog : subCatalogs) {
            CatalogTree subTree = new CatalogTree();
            subTree.setRoot(subCatalog);
            catalogTree.getSubCatalog().add(subTree);
            buildCataLogTree(subTree, subCatalog.getCatalogID());
        }
    }

    public static CatalogTree getTree() {
        return tree;
    }

    public long addCatalog(long rootID, String catalogName) {
        Catalog catalog = new Catalog();
        catalog.setParentCatalog(rootID);
        catalog.setCatalogName(catalogName);
        return DBConnection.addCataLog(catalog);
    }
    
    public boolean deleteCatalog(long catalogID){
        return DBConnection.deleteCatalog(catalogID);
    }
    
    public boolean updateCatalog(long catalogID, String catalogName){
        return DBConnection.updateCatalogName(catalogID, catalogName);
    }
    
    public static void main(String[] args){
        tree.print();
    }
}
