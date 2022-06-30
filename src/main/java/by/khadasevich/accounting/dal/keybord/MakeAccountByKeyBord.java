package by.khadasevich.accounting.dal.keybord;

import by.khadasevich.accounting.entities.Account;

public interface MakeAccountByKeyBord {
    /**
     * Make new Account instance with fields entered by keyboard.
     * Account id field will be zero.
     * Account balance field will be zero.
     * @return Account instance.
     */
    Account makeAccount();
}
