package by.khadasevich.accounting.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Account {
    /**
     * Max possible Account currency length.
     */
    public static final int MAX_ACCOUNT_CURRENCY_LENGTH = 10;
    /**
     * Maximum value of balance.
     */
    public static final double MAX_BALANCE_VALUE = 2000000000;
    /**
     * Minimum value of balance.
     */
    private static final double MIN_BALANCE_VALUE = 0;
    /**
     * Message if illegal balance.
     */
    private static final String ILLEGAL_BALANCE_VALUE =
            String.format("Illegal balance. Balance must be"
                            + " in range [%.3f; %,.3f]",
                    MIN_BALANCE_VALUE, MAX_BALANCE_VALUE);
    /**
     *  Unique account indicator in database.
     */
    private int id;
    /**
     * Reference to unique indicator of user in database.
     */
    private int userId;
    /**
     * Account balance.
     */
    private double balance;
    /**
     * Account currency.
     */
    private String currency;
    /**
     * Set balance value.
     * Throw IllegalArgumenException if balance out of range.
     * @param accountBalance is value to set.
     */
    public void setBalance(final double accountBalance) {
        if (MIN_BALANCE_VALUE <= balance && balance <= MAX_BALANCE_VALUE) {
            this.balance = accountBalance;
        } else {
            throw new IllegalArgumentException(ILLEGAL_BALANCE_VALUE);
        }
    }
    /**
     * Set Account [currency] value.
     * it is no longer then MAX_ACCOUNT_CURRENCY_LENGTH
     * If [accountCurrency] is longer then MAX_ACCOUNT_CURRENCY_LENGTH
     * it is truncated.
     * @param accountCurrency is Account currency to set.
     */
    public void setCurrency(final String accountCurrency) {
        if (accountCurrency.length() > MAX_ACCOUNT_CURRENCY_LENGTH) {
            this.currency = accountCurrency.substring(0,
                    MAX_ACCOUNT_CURRENCY_LENGTH);
        } else {
            this.currency = accountCurrency;
        }
    }
}
