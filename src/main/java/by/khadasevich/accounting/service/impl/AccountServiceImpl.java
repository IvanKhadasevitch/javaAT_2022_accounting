package by.khadasevich.accounting.service.impl;

import by.khadasevich.accounting.dal.dao.AccountDao;
import by.khadasevich.accounting.dal.dao.UserDao;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.entities.User;
import by.khadasevich.accounting.service.AccountService;
import by.khadasevich.accounting.service.ServiceException;
import by.khadasevich.accounting.util.SingletonBuilder;

import java.sql.SQLException;

public class AccountServiceImpl extends AbstractService
        implements AccountService {
    /**
     * Singleton Instance of UserDao.
     */
    private final UserDao userDao =
            SingletonBuilder.getInstanceImpl(UserDao.class);
    /**
     * Singleton Instance of AccountDao.
     */
    private final AccountDao accountDao =
            SingletonBuilder.getInstanceImpl(AccountDao.class);
    /**
     * Create new account for User.
     * Account with [balance] = zero
     * For one User possible one account with definite currency.
     * Created successfully account is saved in database.
     *
     * @param userId   is User id
     * @param currency - account currency
     * @return new account or null if impossible to create or save account.
     */
    @Override
    public Account addAccountToUser(final int userId, final String currency) {
        if (currency != null) {
            Account account = new Account();
            try {
                this.startTransaction();
                // check in database existence User with userID
                checkUserByIdInDatabase(userId);
                // check in database if already exist account
                // with [userId] and [currency]
                checkAccountByUserIdAndCurrencyInDatabase(userId, currency);
                // fill parameters for new account
                account.setUserId(userId);
                account.setBalance(0);
                account.setCurrency(currency);
                // save account in database
                account = accountDao.save(account);
                this.commit();
                this.stopTransaction();
                // return saved account
                return account;
            } catch (SQLException exp) {
                this.rollback();
                String errorMessage = "Error saving Account: " + account;
                System.err.println(errorMessage + exp.getMessage());
                throw new ServiceException(errorMessage);
            }
        } else {
            return null;
        }
    }
    /**
     * Check in database existence User with userID.
     * @param userId is unique User id
     * @throws SQLException if User not exist in database
     */
    private void checkUserByIdInDatabase(final int userId)
            throws SQLException {
        User user = userDao.get(userId);
        if (user == null) {
            String errorMessage = "In database not exist User with id:"
                    + userId;
            throw new SQLException(errorMessage);
        }
    }
    /**
     * Check in database if already exist account with [userId] and [currency].
     * @param userId - unique User id
     * @param currency - account currency
     * @throws SQLException - if not exist in database account with
     * User_id [userId] and currency [currency]
     */
    private void checkAccountByUserIdAndCurrencyInDatabase(final int userId,
                                                         final String currency)
            throws SQLException {
        Account accountToCheck =
                accountDao.getAccountByUserIdAndCurrency(userId, currency);
        if (accountToCheck != null) {
            String errorMessage = String.format("Account in"
                    + " currency[%s] already exist for User"
                    + " with id[%d]", currency, userId);
            throw new SQLException(errorMessage);
        }
    }
}
