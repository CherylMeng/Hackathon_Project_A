package oracle.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Test {
    public Test() {
        super();
    }

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("Model");
        EntityManager em = factory.createEntityManager();
		Query query = em.createNativeQuery("SELECT count(1) from user");
		int count = Integer.parseInt(query.getSingleResult().toString());
                System.out.print(count);
    }
}
