package by.khadasevich.accounting.util;

import by.khadasevich.accounting.entities.Account;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Encapsulate some data reading from keyboard.
 */
public final class KeyBordReader {
    /**
     * Message for User before Account currency entering.
     */
    private static final String ENTER_ACCOUNT_CURRENCY_MESSAGE =
            "Enter Account currency no longer %d chars: ";

    // override default constructor
    private KeyBordReader() {
        // utility class - no realisation needed.
    }
    /**
     * Take entered by keyboard integer id.
     * @param message - message for User before entering.
     * @return integer id
     */
    public static int takeIntId(final String message) {
        // take integer id
        System.out.println(message);
        boolean isStopRead = false;
        int id = 0;
        do {
            try {
                Scanner scanner = new Scanner(System.in);
                id = scanner.nextInt();
                isStopRead = true;
            } catch (NoSuchElementException exp) {
                System.out.println("Invalid integer format");
            }
        } while (!isStopRead);
        return id;
    }
    /**
     * Take entered by keyboard Account currency.
     * @return Account currency
     * it is not empty and no longer then Account.MAX_ACCOUNT_CURRENCY_LENGTH
     */
    public static String takeCurrency() {
        // take user's name
        System.out.printf(ENTER_ACCOUNT_CURRENCY_MESSAGE,
                Account.MAX_ACCOUNT_CURRENCY_LENGTH);
        boolean isStopRead = false;
        String accountCurrency = null;
        do {
            Scanner scanner = new Scanner(System.in);
            String enteredCurrency = scanner.nextLine();
            if (isValidCurrency(enteredCurrency)) {
                isStopRead = true;
                if (enteredCurrency.length()
                        > Account.MAX_ACCOUNT_CURRENCY_LENGTH) {
                    accountCurrency = enteredCurrency.substring(0,
                            Account.MAX_ACCOUNT_CURRENCY_LENGTH);
                } else {
                    accountCurrency = enteredCurrency;
                }
            }
        } while (!isStopRead);
        return accountCurrency;
    }
    private static boolean isValidCurrency(final String currency) {
        if (currency == null) {
            return false;
        } else {
            return !(currency.isEmpty());
        }
    }
}
