package by.khadasevich.accounting.dal.keybord.impl;

import by.khadasevich.accounting.dal.keybord.MakeAccountByKeyBord;
import by.khadasevich.accounting.entities.Account;
import by.khadasevich.accounting.util.KeyBordReader;

public final class MakeAccountByKeyBordImpl implements MakeAccountByKeyBord {
    // to override default public constructor
    private MakeAccountByKeyBordImpl() {
        // no realisation needed
    }
    /**
     * Make new Account instance with fields entered by keyboard.
     * Account id field will be zero.
     * Account balance field will be zero.
     *
     * @return Account instance.
     */
    @Override
    public Account makeAccount() {
        Account account = new Account();
        account.setUserId(KeyBordReader.takeIntId("Enter User's id as integer"
                + " (for example:1) :"));
        account.setCurrency(KeyBordReader.takeCurrency());
        return account;
    }
}
