package by.khadasevich.accounting.dal.dao.impl;

import by.khadasevich.accounting.dal.dao.AccountDao;
import by.khadasevich.accounting.dal.dao.DbOperationException;
import by.khadasevich.accounting.entities.Account;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class AccountDaoImpl extends AbstractDao implements AccountDao {
    /**
     * Query expression to database to save Account.
     */
    private static final String SAVE_ACCOUNT_SQL = "INSERT INTO Accounts"
            + " (user_id,balance,currency) VALUES (?,?,?)";
    /**
     * User_id parameter index in SAVE_ACCOUNT_SQL.
     */
    private static final int USER_ID_PARAM_INDEX_IN_SAVE_SQL = 1;
    /**
     * Balance parameter index in SAVE_ACCOUNT_SQL.
     */
    private static final int BALANCE_PARAM_INDEX_IN_SAVE_SQL = 2;
    /**
     * Currency parameter index in SAVE_ACCOUNT_SQL.
     */
    private static final int CURRENCY_PARAM_INDEX_IN_SAVE_SQL = 3;
    /**
     * Query expression to database to get Account by id.
     */
    private static final String GET_ACCOUNT_BY_ID =
            "SELECT * FROM Accounts WHERE account_id=?";
    /**
     * Query expression to database to get Account by User id and currency.
     */
    private static final String GET_ACCOUNT_BY_USER_ID_CURRENCY =
            "SELECT * FROM Accounts WHERE user_id=? AND currency=?";
    /**
     * Query expression to database to update Account with definite id.
     */
    private static final String UPDATE_ACCOUNT_BY_ID =
            "UPDATE Accounts SET user_id=?, balance=?, currency=?"
                    + " WHERE account_id=?";
    /**
     * User_id parameter index in UPDATE_ACCOUNT_BY_ID.
     */
    private static final int USER_ID_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID = 1;
    /**
     * Balance parameter index in UPDATE_ACCOUNT_BY_ID.
     */
    private static final int BALANCE_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID = 2;
    /**
     * Currency parameter index in UPDATE_ACCOUNT_BY_ID.
     */
    private static final int CURRENCY_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID = 3;
    /**
     * Account_id parameter index in UPDATE_ACCOUNT_BY_ID.
     */
    private static final int ACCOUNT_ID_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID =
            4;
    /**
     * Error message when Can't execute SQL.
     */
    private static final String CANT_EXECUTE_SQL = "Can't execute SQL: ";
    /**
     * Name of column [account_id] in table [Account] in database table.
     */
    private static final String ACCOUNT_ID_COLUMN = "account_id";
    /**
     * Name of column [user_id] in table [Account] in database table.
     */
    private static final String ACCOUNT_USER_ID_COLUMN = "user_id";
    /**
     * Name of column [balance] in table [Account] in database table.
     */
    private static final String ACCOUNT_BALANCE_COLUMN = "balance";
    /**
     * Name of column [currency] in table [Account] in database table.
     */
    private static final String ACCOUNT_CURRENCY_COLUMN = "currency";

    private AccountDaoImpl() {
        // to override public constructor by default
    }
    /**
     * Saves the entity type <Account> in the database if @param t != null.
     *
     * @param account determine entity with type <Account>
     * @return saved entity with not null id or
     * null if [account] = null
     * @throws DbOperationException when can't save entity
     */
    @Override
    public Account save(final Account account) throws DbOperationException {
        if (account == null) {
            return null;
        }
        // initialise psSave
        try (PreparedStatement psSave = prepareStatement(SAVE_ACCOUNT_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            // set params to query
            psSave.setInt(USER_ID_PARAM_INDEX_IN_SAVE_SQL,
                    account.getUserId());
            psSave.setDouble(BALANCE_PARAM_INDEX_IN_SAVE_SQL,
                    account.getBalance());
            psSave.setString(CURRENCY_PARAM_INDEX_IN_SAVE_SQL,
                    account.getCurrency());
            psSave.executeUpdate();
            // take Account id
            ResultSet rs = psSave.getGeneratedKeys();
            if (rs.next()) {
                account.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            String errorMessage = CANT_EXECUTE_SQL + e.getMessage();
            System.err.println(errorMessage);
            throw new DbOperationException(errorMessage);
        }
        return account;
    }
    /**
     * Returns an Account with an id from the database.
     *
     * @param id is id of entity in database
     * @return entity with type <Account> from the database,
     * or null if such an entity was not found
     * @throws DbOperationException if there is an error connecting
     * to the database
     */
    @Override
    public Account get(final Serializable id) throws DbOperationException {
        try (PreparedStatement psGet = prepareStatement(GET_ACCOUNT_BY_ID)) {
            psGet.setInt(1, (int) id);
            ResultSet rs = psGet.executeQuery();
            if (rs.next()) {
                return populateEntity(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new DbOperationException(e.getMessage());
        }
        return null;
    }
    /**
     * Get account from database in [currency] for User with id [userId].
     *
     * @param userId   is User id
     * @param currency - account currency
     * @return Account instance from database or null if it not exists.
     */
    @Override
    public Account getAccountByUserIdAndCurrency(final int userId,
                                                 final String currency) {
        try (PreparedStatement psGetByUserIdCurrency =
                     prepareStatement(GET_ACCOUNT_BY_USER_ID_CURRENCY)) {
            psGetByUserIdCurrency.setInt(1, userId);
            psGetByUserIdCurrency.setString(2, currency);
            ResultSet rs = psGetByUserIdCurrency.executeQuery();
            if (rs.next()) {
                return populateEntity(rs);
            }
            rs.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new DbOperationException(e.getMessage());
        }
        return null;
    }
    /**
     * Update an Account with an id = account.id in the database.
     * If @param account != null
     *
     * @param account determine a new Account to be updated
     *                in the database with id = account.id
     * @throws DbOperationException if there is an error updating Account
     *                      in the database
     */
    @Override
    public void update(final Account account) throws DbOperationException {
        if (account == null) {
            return;
        }
        try (PreparedStatement psUpdate =
                     prepareStatement(UPDATE_ACCOUNT_BY_ID)) {
            psUpdate.setInt(ACCOUNT_ID_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID,
                    account.getId());
            psUpdate.setInt(USER_ID_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID,
                    account.getUserId());
            psUpdate.setDouble(BALANCE_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID,
                    account.getBalance());
            psUpdate.setString(CURRENCY_PARAM_INDEX_IN_UPDATE_ACCOUNT_BY_ID,
                    account.getCurrency());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new DbOperationException(e.getMessage());
        }
    }
    /**
     * Fill Account fields.
     * @param rs is ResultSet instance after request GET_ACCOUNT_BY_ID
     * @return instance of Account with filled fields
     * @throws SQLException if the columnLabel is not valid;
     * if a database access error occurs
     * or this method is called on a closed result set
     */
    private Account populateEntity(final ResultSet rs) throws SQLException {
        Account entity = new Account();
        entity.setId(rs.getInt(ACCOUNT_ID_COLUMN));
        entity.setUserId(rs.getInt(ACCOUNT_USER_ID_COLUMN));
        entity.setBalance(rs.getDouble(ACCOUNT_BALANCE_COLUMN));
        entity.setCurrency(rs.getString(ACCOUNT_CURRENCY_COLUMN));

        return entity;
    }
}
