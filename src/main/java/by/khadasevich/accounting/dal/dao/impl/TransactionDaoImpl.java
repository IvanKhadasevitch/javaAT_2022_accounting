package by.khadasevich.accounting.dal.dao.impl;

import by.khadasevich.accounting.dal.dao.DbOperationException;
import by.khadasevich.accounting.dal.dao.TransactionDao;
import by.khadasevich.accounting.entities.Transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDaoImpl extends AbstractDao implements TransactionDao {
    /**
     * Query expression to database to save Transaction.
     */
    private static final String SAVE_TRANSACTION_SQL = "INSERT INTO"
            + " Transactions (account_id,amount) VALUES (?,?)";
    /**
     * Error message when Can't execute SQL.
     */
    private static final String CANT_EXECUTE_SQL = "Can't execute SQL: ";
    /**
     * Saves the Transaction in the database if @param transaction != null.
     *
     * @param transaction determine entity with type <Transaction>
     * @return saved Transaction with not null id or
     * null if transaction = null
     * @throws DbOperationException when can't save Transaction
     */
    @Override
    public Transaction save(final Transaction transaction)
            throws DbOperationException {
        if (transaction == null) {
            return null;
        }
        // initialise psSave
        try (PreparedStatement psSave = prepareStatement(SAVE_TRANSACTION_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            // set params to query
            psSave.setInt(1, transaction.getAccountId());
            psSave.setDouble(2, transaction.getAmount());
            psSave.executeUpdate();
            // take Transaction id
            ResultSet rs = psSave.getGeneratedKeys();
            if (rs.next()) {
                transaction.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            String errorMessage = CANT_EXECUTE_SQL + e.getMessage();
            System.err.println(errorMessage);
            throw new DbOperationException(errorMessage);
        }
        return transaction;
    }
}
