package by.khadasevich.accounting.dal.dao;

/**
 * Describe common functions for all kind of entities.
 * @param <T> determine entity with type <T>
 */
public interface DAO<T> {
    /**
     *
     * Saves the entity type <T> in the database if @param t != null.
     *
     * @param t determine entity with type <T>
     * @return saved entity with not null id or
     *         null if t = null
     * @throws DbOperationException when can't save entity
     */
    T save(T t) throws DbOperationException;
}
