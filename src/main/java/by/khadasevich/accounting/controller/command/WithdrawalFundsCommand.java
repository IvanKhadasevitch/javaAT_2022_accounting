package by.khadasevich.accounting.controller.command;

import by.khadasevich.accounting.dal.dao.AccountDao;
import by.khadasevich.accounting.dal.keybord.MakeTransactionByKeyBord;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.entities.Transaction;
import by.khadasevich.accounting.service.TransactionService;
import by.khadasevich.accounting.util.SingletonBuilder;

/**
 * Implementation Command that withdrawal founds from user account.
 * New transaction is saved in database.
 */
public class WithdrawalFundsCommand implements Command {
    /**
     * Execute command that withdrawal founds from User account.
     * New transaction is saved in database.
     * Transaction amount will be negative
     */
    @Override
    public void execute() {
        // form new Transaction to save
        Transaction transaction =
                SingletonBuilder.getInstanceImpl(MakeTransactionByKeyBord.class)
                        .makeTransaction();
        // change sign of transaction amount
        transaction.setAmount(-transaction.getAmount());
        System.out.println("Transaction before assign to account: "
                + transaction);
        // make founds withdrawal
        transaction =
                SingletonBuilder.getInstanceImpl(TransactionService.class)
                        .addTransactionToAccount(transaction.getAccountId(),
                                transaction.getAmount());
        if (transaction != null) {
            // take Account with new balance
            Account account =
                    SingletonBuilder.getInstanceImpl(AccountDao.class)
                            .get(transaction.getAccountId());
            // present result of withdrawing funds
            System.out.println("Account with new balance: " + account);
            System.out.println("Transaction after assign to account: "
                    + transaction);
        }
    }
}
