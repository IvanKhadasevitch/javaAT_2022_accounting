package by.khadasevich.accounting.service.impl;

import by.khadasevich.accounting.dal.dao.AccountDao;
import by.khadasevich.accounting.dal.dao.TransactionDao;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.entities.Transaction;
import by.khadasevich.accounting.service.ServiceException;
import by.khadasevich.accounting.service.TransactionService;
import by.khadasevich.accounting.util.SingletonBuilder;

import java.sql.SQLException;

public class TransactionServiceImpl extends AbstractService
        implements TransactionService {
    /**
     * TransactionDao singleton instance.
     */
    private final TransactionDao transactionDao =
            SingletonBuilder.getInstanceImpl(TransactionDao.class);
    /**
     * AccountDao singleton instance.
     */
    private final AccountDao accountDao =
            SingletonBuilder.getInstanceImpl(AccountDao.class);
    /**
     * Associate new Transaction with User's account.
     * After transaction account balance must be
     * greater zero and less than Account.MAX_BALANCE_VALUE.
     * If transaction successful it change account balance
     * and is saved in database.
     *
     * @param accountId - account id to transaction operate
     * @param amount    - transaction amount
     * @return - saved in database Transaction with id != 0
     */
    @Override
    public Transaction addTransactionToAccount(final int accountId,
                                               final double amount) {
        if (amount != 0) {
            Transaction transaction = new Transaction();
            try {
                this.startTransaction();
                // check existence of account with accountId in database
                Account account = accountDao.get(accountId);
                if (account == null) {
                    String errorMessage = String.format("It doesn't exist"
                            + " account with id [%d]", accountId);
                    throw new SQLException(errorMessage);
                }
                // check if balance after transaction is unsuitable
                if (!isSuitableTransactionAmount(amount,
                        account.getBalance())) {
                    throw new SQLException("Unsuitable transaction amount");
                }
                // all transaction parameters suitable, update Account balance
                double newBalance = amount + account.getBalance();
                account.setBalance(newBalance);
                accountDao.update(account);
                // make transaction
                transaction.setAccountId(accountId);
                transaction.setAmount(amount);
                // save account in database
                transaction = transactionDao.save(transaction);
                this.commit();
                this.stopTransaction();
                // return saved account
                return transaction;
            } catch (SQLException exp) {
                this.rollback();
                String errorMessage = "Error saving Transaction: "
                        + transaction;
                System.err.println(errorMessage + exp.getMessage());
                throw new ServiceException(errorMessage);
            }
        } else {
            return null;
        }
    }
    private boolean isSuitableTransactionAmount(final double transactionAmount,
                                                final double accountBalance) {
        if (Math.abs(transactionAmount) > Transaction.MAX_TRANSACTION_AMOUNT) {
            return false;
        } else {
            double result = transactionAmount + accountBalance;
            return (0 <= result) && (result <= Account.MAX_BALANCE_VALUE);
        }
    }
}
