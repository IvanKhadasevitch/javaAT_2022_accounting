package by.khadasevich.accounting.dal.dao;

import by.khadasevich.accounting.entities.User;

import java.io.Serializable;

/**
 * Describe functions for User entity.
 */
public interface UserDao extends DAO<User> {
    /**
     *
     * Returns an User with an id from the database.
     *
     * @param id determine id of User in database
     * @return entity with type <User> from the database, or null if such an
     * entity was not found
     * @throws DbOperationException if there is an error connecting
     * to the database
     */
    User get(Serializable id) throws DbOperationException;
}
