package by.khadasevich.accounting.dal.dao;

import by.khadasevich.accounting.entities.Account;

import java.io.Serializable;

/**
 * Describe functions for Account entity.
 */
public interface AccountDao extends DAO<Account> {
    /**
     * Get account from database in [currency] for User with id [userId].
     * @param userId is User id
     * @param currency - account currency
     * @return Account instance from database or null if it not exists.
     */
    Account getAccountByUserIdAndCurrency(int userId, String currency);
    /**
     *
     * Returns an Account with an id from the database.
     *
     * @param id determine id of Account in database
     * @return entity with type <Account> from the database, or null if such an
     * entity was not found
     * @throws DbOperationException if there is an error connecting
     * to the database
     */
    Account get(Serializable id) throws DbOperationException;
    /**
     *
     * Update an Account with an id = account.id in the database.
     * If @param account != null
     *
     * @param account determine a new Account to be updated
     *                in the database with id = account.id
     * @throws DbOperationException if there is an error updating Account
     * in the database
     */
    void update(Account account) throws DbOperationException;
}
