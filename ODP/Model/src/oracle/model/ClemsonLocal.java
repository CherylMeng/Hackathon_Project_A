package oracle.model;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ClemsonLocal {
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    <T> T persistEntity(T entity);

    <T> T mergeEntity(T entity);

    SSubType persistSSubType(SSubType SSubType);

    SSubType mergeSSubType(SSubType SSubType);

    void removeSSubType(SSubType SSubType);

    List<SSubType> getSSubTypeFindAll();

    SType persistSType(SType SType);

    SType mergeSType(SType SType);

    void removeSType(SType SType);

    List<SType> getSTypeFindAll();
}
