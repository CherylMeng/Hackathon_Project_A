package oracle.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPADBConnection { 
    private EntityManagerFactory entityFactory;
    private EntityManager em;
    private static JPADBConnection singleton;
    private static Object lock = new Object();
    public static JPADBConnection getInstance(){
        synchronized(lock){
            if(singleton == null)singleton = new JPADBConnection();
            return singleton;
        }
    }
     
    public EntityManagerFactory getEntityManagerFactory(){
        if (entityFactory == null) {
            entityFactory = Persistence.createEntityManagerFactory("Model");
       }
        return entityFactory;
    }
    public static void main(String[] args) {
        JPADBConnection jPADBConnection = new JPADBConnection();
    }
}
