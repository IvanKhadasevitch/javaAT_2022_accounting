package by.khadasevich.accounting.dal.keybord;

import by.khadasevich.accounting.entities.Transaction;

public interface MakeTransactionByKeyBord {
    /**
     * Make new Transaction instance with fields entered by keyboard.
     * Without id field.
     * @return Transaction instance where id is 0.
     */
    Transaction makeTransaction();
}
