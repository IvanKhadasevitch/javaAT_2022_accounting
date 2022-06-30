package by.khadasevich.accounting.controller.command;

import by.khadasevich.accounting.dal.db.ConnectionManager;
import by.khadasevich.accounting.dal.keybord.MakeAccountByKeyBord;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.service.AccountService;
import by.khadasevich.accounting.util.SingletonBuilder;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Implementation Command that add new account to user.
 * New account is saved in database.
 */
public class AddAccountToUserCommand implements Command {
    /**
     * Execute command that add new account to user.
     * New account is saved in database.
     */
    @Override
    public void execute() {
        // form new  Account to save
        Account account =
                SingletonBuilder.getInstanceImpl(MakeAccountByKeyBord.class)
                        .makeAccount();
        System.out.println("Account before assign to User: " + account);
        // save Account in database
        AccountService accountService =
                SingletonBuilder.getInstanceImpl(AccountService.class);
        account = accountService.addAccountToUser(account.getUserId(),
                account.getCurrency());
        System.out.println("Account after assign: " + account);
        // close connection to database
        ConnectionManager.closeConnection();
    }
}
