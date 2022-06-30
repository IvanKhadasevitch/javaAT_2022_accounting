package by.khadasevich.accounting.service;

import by.khadasevich.accounting.entities.Account;

public interface AccountService {
    /**
     * Create new account for User.
     * Account with [balance] = zero
     * For one User possible one account with definite currency.
     * Created successfully account is saved in database.
     * @param userId is User id
     * @param currency - account currency
     * @return new account or null if impossible to create or save account.
     */
    Account addAccountToUser(int userId, String currency);
}
