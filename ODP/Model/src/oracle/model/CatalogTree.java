package oracle.model;

import java.util.ArrayList;

public class CatalogTree {
    private Catalog root;
    
    private ArrayList<CatalogTree> subCatalog;

    public void setRoot(Catalog root) {
        this.root = root;
    }

    public Catalog getRoot() {
        return root;
    }

    public void setSubCatalog(ArrayList<CatalogTree> subCatalog) {
        this.subCatalog = subCatalog;
    }

    public ArrayList<CatalogTree> getSubCatalog() {
        return subCatalog;
    }
    
    public void print(){
        for(int i = 0; i < root.getCatalogDepth(); i++)
            System.out.print(" ");
        System.out.println(root.getCatalogName());
        if (root == null || subCatalog == null || subCatalog.size() == 0) return;
        for(CatalogTree subCatalog : subCatalog){
            subCatalog.print();
        }
    }
}
