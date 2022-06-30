package by.khadasevich.accounting.dal.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class to operate with connection to database.
 */
public final class ConnectionManager {
    /**
     * Message for exception when impossible to connect with database.
     */
    private static final String EXCEPTION_MESSAGE = "Can't open database.";
    /**
     * Message for exception when impossible to close connection with database.
     */
    private static final String EXCEPTION_CANT_CLOSE_CONNECTION =
            "Can't close connection with database.";
    /**
     * TreadLocal variable to store Connection.
     */
    private static final ThreadLocal<Connection> TL = new ThreadLocal<>();

    private ConnectionManager() {
        // utility class, no realisation needed
    }
    /**
     * Get Connection with database for current thread.
     * @return Connection instance
     * @throws DbManagerException if impossible database open
     */
    public static Connection getConnection() throws DbManagerException {
        try {
            if (TL.get() == null) {
                TL.set(DataSource.getInstance().getConnection());
            }
            return TL.get();
        } catch (Exception e) {
            throw new DbManagerException(EXCEPTION_MESSAGE +  e.getMessage());
        }
    }
    /**
     * Close Connection with database for current thread.
     * @throws DbManagerException if impossible close connection
     */
    public static void closeConnection() throws DbManagerException {
        Connection connection = TL.get();
        if (connection != null) {
            try {
                connection.close();
                TL.set(null);
            } catch (SQLException exp) {
                System.err.println(exp.getMessage());
                throw new DbManagerException(EXCEPTION_CANT_CLOSE_CONNECTION);
            }
        }
    }
}
