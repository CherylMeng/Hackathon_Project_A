package oracle.model;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless(name = "Clemson", mappedName = "HR_EJB_JPA-Model-Clemson")
public class ClemsonBean implements Clemson, ClemsonLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;

    public ClemsonBean() {
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }
        return query.getResultList();
    }

    public <T> T persistEntity(T entity) {
        em.persist(entity);
        return entity;
    }

    public <T> T mergeEntity(T entity) {
        return em.merge(entity);
    }

    public SSubType persistSSubType(SSubType SSubType) {
        em.persist(SSubType);
        return SSubType;
    }

    public SSubType mergeSSubType(SSubType SSubType) {
        return em.merge(SSubType);
    }

    public void removeSSubType(SSubType SSubType) {
        SSubType = em.find(SSubType.class, SSubType.getSub_type_id());
        em.remove(SSubType);
    }

    /** <code>select o from SSubType o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SSubType> getSSubTypeFindAll() {
        return em.createNamedQuery("SSubType.findAll", SSubType.class).getResultList();
    }

    public SType persistSType(SType SType) {
        em.persist(SType);
        return SType;
    }

    public SType mergeSType(SType SType) {
        return em.merge(SType);
    }

    public void removeSType(SType SType) {
        SType = em.find(SType.class, SType.getType_id());
        em.remove(SType);
    }

    /** <code>select o from SType o</code> */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<SType> getSTypeFindAll() {
        return em.createNamedQuery("SType.findAll", SType.class).getResultList();
    }
}
