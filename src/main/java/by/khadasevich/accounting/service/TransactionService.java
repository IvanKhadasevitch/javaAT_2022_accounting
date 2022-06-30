package by.khadasevich.accounting.service;

import by.khadasevich.accounting.entities.Transaction;

public interface TransactionService {
    /**
     * Associate new Transaction with User's account.
     * After transaction account balance must be
     * greater zero and less than Account.MAX_BALANCE_VALUE.
     * If transaction successful it change account balance
     * and is saved in database.
     * @param accountId - account id to transaction operate
     * @param amount - transaction amount
     * @return - saved in database Transaction with id != 0
     */
    Transaction addTransactionToAccount(int accountId, double amount);
}
