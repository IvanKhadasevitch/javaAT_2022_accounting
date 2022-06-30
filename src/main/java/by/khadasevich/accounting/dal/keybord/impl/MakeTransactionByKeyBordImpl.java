package by.khadasevich.accounting.dal.keybord.impl;

import by.khadasevich.accounting.dal.keybord.MakeTransactionByKeyBord;
import by.khadasevich.accounting.entities.Transaction;
import by.khadasevich.accounting.util.KeyBordReader;

import java.util.NoSuchElementException;
import java.util.Scanner;

public final class MakeTransactionByKeyBordImpl
        implements MakeTransactionByKeyBord {
    // to override default public constructor
    private MakeTransactionByKeyBordImpl() {
        // no realisation needed
    }
    /**
     * Make new Transaction instance with fields entered by keyboard.
     * Without id field.
     *
     * @return Transaction instance where id is 0.
     */
    @Override
    public Transaction makeTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAccountId(KeyBordReader.takeIntId("Enter User's account"
                + " id as integer (for example:1) :"));
        String message = String.format("Enter transfer amount less then"
                + " [%,.3f] (for example 1,237):",
                Transaction.MAX_TRANSACTION_AMOUNT);
        transaction.setAmount(takeAmount(message));
        return transaction;
    }

    /**
     * Take entered by keyboard double amount.
     * @param message - message for User before entering.
     * @return double amount
     */
    private double takeAmount(final String message) {
        // take double amount
        System.out.println(message);
        boolean isStopRead = false;
        double amount = 0;
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                amount = scanner.nextDouble();
                if (amount < 0) {
                    amount = -amount;
                }
                isStopRead = true;
            } catch (NoSuchElementException exp) {
                System.out.println("Invalid double format");
            }
        } while (!isStopRead);
        return amount;
    }
}
