package by.khadasevich.accounting.controller.command;

import by.khadasevich.accounting.dal.dao.AccountDao;
import by.khadasevich.accounting.dal.keybord.MakeTransactionByKeyBord;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.entities.Transaction;
import by.khadasevich.accounting.service.TransactionService;
import by.khadasevich.accounting.util.SingletonBuilder;

/**
 * Implementation Command that depositing founds to user account.
 * New transaction is saved in database.
 */
public class DepositingFundsCommand implements Command {
    /**
     * Command that depositing founds to user account.
     * New transaction is saved in database.
     */
    @Override
    public void execute() {
        // form new Transaction to save
        Transaction transaction =
               SingletonBuilder.getInstanceImpl(MakeTransactionByKeyBord.class)
                        .makeTransaction();
        System.out.println("Transaction before assign to account: "
                + transaction);
        // make funds depositing
        transaction =
                SingletonBuilder.getInstanceImpl(TransactionService.class)
                        .addTransactionToAccount(transaction.getAccountId(),
                                transaction.getAmount());
        if (transaction != null) {
            // take Account with new balance
            Account account =
                    SingletonBuilder.getInstanceImpl(AccountDao.class)
                            .get(transaction.getAccountId());
            // present result of depositing funds
            System.out.println("Account with new balance: " + account);
            System.out.println("Transaction after assign to account: "
                    + transaction);
        }
    }
}
