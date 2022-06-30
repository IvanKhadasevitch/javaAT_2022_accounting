package by.khadasevich.accounting.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Data
@NoArgsConstructor
public class Transaction {
    /**
     * Maximum amount of transaction.
     */
    public static final double MAX_TRANSACTION_AMOUNT = 100000000;
    /**
     * Max number digits after point of transaction amount.
     */
    private static final int NUMBER_DIGITS_AFTER_POINT_OF_AMOUNT = 3;
    /**
     * Message if illegal transaction amount.
     */
    private static final String ILLEGAL_TRANSACTION_AMOUNT =
            String.format("Illegal transaction amount. Amount must be"
                            + " in range (%.3f; %,.3f]",
                    0.0, MAX_TRANSACTION_AMOUNT);
    /**
     *  Unique transaction indicator in database.
     */
    private int id;
    /**
     * Reference to unique indicator of user account in database.
     */
    private int accountId;
    /**
     * Transaction amount.
     * Must be in range.
     */
    private double amount;
    /**
     * Set transaction amount.
     * If transaction amount out of range throw IllegalArgumentException.
     * @param transactionAmount is amount of transaction
     */
    public void setAmount(double transactionAmount) {
        // around transaction amount to NUMBER_DIGITS_AFTER_POINT_OF_AMOUNT
        BigDecimal bd = new BigDecimal(Double.toString(transactionAmount));
        bd = bd.setScale(NUMBER_DIGITS_AFTER_POINT_OF_AMOUNT,
                RoundingMode.HALF_UP);
        transactionAmount = bd.doubleValue();
        // check range of Transaction amount
        if (transactionAmount != 0
                && Math.abs(transactionAmount) <= MAX_TRANSACTION_AMOUNT) {
            this.amount = transactionAmount;
        } else {
            throw new IllegalArgumentException(ILLEGAL_TRANSACTION_AMOUNT);
        }
    }
}
