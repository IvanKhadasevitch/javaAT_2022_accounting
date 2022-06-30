package by.khadasevich.accounting.dal.dao.impl;

import by.khadasevich.accounting.dal.dao.DbOperationException;
import by.khadasevich.accounting.dal.dao.UserDao;
import by.khadasevich.accounting.entities.User;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class UserDaoImpl extends AbstractDao implements UserDao {
    /**
     * Query expression to database to save User.
     */
    private static final String SAVE_USER_SQL = "INSERT INTO Users"
            + " (name,address) VALUES (?,?)";
    /**
     * Index of name parameter in SAVE_USER_SQL.
     */
    private static final int USER_NAME_PARAM_INDEX_IN_SAVE_USER_SQL = 1;
    /**
     * Index of address parameter in SAVE_USER_SQL.
     */
    private static final int USER_ADDRESS_PARAM_INDEX_IN_SAVE_USER_SQL = 2;
    /**
     * Query expression to database to get user by id.
     */
    private static final String GET_USER_BY_ID =
            "SELECT * FROM Users WHERE user_id=?";
    /**
     * Name of column [user_id] in table [User] in database table.
     */
    private static final String USER_ID_COLUMN = "user_id";
    /**
     * Name of column [name] in table [User] in database table.
     */
    private static final String USER_NAME_COLUMN = "name";
    /**
     * Name of column [address] in table [User] in database table.
     */
    private static final String USER_ADDRESS_COLUMN = "address";
    /**
     * Error message when Can't execute SQL.
     */
    private static final String CANT_EXECUTE_SQL = "Can't execute SQL: ";

    private UserDaoImpl() {
        // to override public constructor by default
    }
    /**
     * Saves User in the database if @param user != null.
     *
     * @param user determine entity User
     * @return saved entity with not null id or
     * null if user = null
     * @throws DbOperationException if can't save User
     */
    @Override
    public User save(final User user) throws DbOperationException {
        if (user == null) {
            return null;
        }
        // initialise psSave
        try (PreparedStatement psSave = prepareStatement(SAVE_USER_SQL,
                Statement.RETURN_GENERATED_KEYS)) {
            // set params to query
            psSave.setString(USER_NAME_PARAM_INDEX_IN_SAVE_USER_SQL,
                    user.getName());
            psSave.setString(USER_ADDRESS_PARAM_INDEX_IN_SAVE_USER_SQL,
                    user.getAddress());
            psSave.executeUpdate();
            // take user id
            ResultSet rs = psSave.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            rs.close();
        } catch (SQLException e) {
            String errorMessage = CANT_EXECUTE_SQL + e.getMessage();
            System.err.println(errorMessage);
            throw new DbOperationException(errorMessage);
        }
        return user;
    }
    /**
     * Returns a User with an id from the database.
     *
     * @param id determine id of User in database
     * @return entity with type <User> from the database,
     *         or null if such an entity was not found
     * @throws DbOperationException if there is an error connecting
     * to the database
     */
    @Override
    public User get(final Serializable id) throws DbOperationException {
        try (PreparedStatement psGet = prepareStatement(GET_USER_BY_ID)) {
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
     * Fill User fields.
     * @param rs is ResultSet instance after request GET_USER_BY_ID
     * @return instance of User with filled fields
     * @throws SQLException if the columnLabel is not valid;
     * if a database access error occurs
     * or this method is called on a closed result set
     */
    private User populateEntity(final ResultSet rs) throws SQLException {
        User entity = new User();
        entity.setId(rs.getInt(USER_ID_COLUMN));
        entity.setName(rs.getString(USER_NAME_COLUMN));
        entity.setAddress(rs.getString(USER_ADDRESS_COLUMN));

        return entity;
    }
}
