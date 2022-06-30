package by.khadasevich.accounting.dal.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class as source of database connections.
 */
public final class DataSource {
    /**
     * Name of file with database properties.
     */
    private static final String DATABASE_PROPERTIES_FILE_NAME =
            "db_accounting";
    /**
     * Database url property name.
     */
    private static final String URL_PROPERTY_NAME = "url";
    /**
     * Database driver property name.
     */
    private static final String DRIVER_PROPERTY_NAME = "driver";
    /**
     * Value for property if property file not define.
     */
    private static final String UNDEFINED_PROPERTY = "UNDEFINED";
    /**
     * Message for exception when impossible to connect with database.
     */
    private static final String EXCEPTION_MESSAGE = "Can't open database.";
    /**
     * DataSource instance as singleton.
     */
    private static DataSource INSTANCE = null;
    /**
     * Lock for thread.
     */
    private static final Lock LOCK = new ReentrantLock();
    /**
     * Database url.
     */
    private final String url;
    /**
     * Database driver.
     */
    private final String driver;
    private DataSource() {
        ResourceBundle rb =
                ResourceBundle.getBundle(DATABASE_PROPERTIES_FILE_NAME);
        if (rb == null) {
            url = UNDEFINED_PROPERTY;
            driver = UNDEFINED_PROPERTY;
        } else {
            url = rb.getString(URL_PROPERTY_NAME);
            driver = rb.getString(DRIVER_PROPERTY_NAME);
        }
    }
    /**
     * Get singleton instance for DataSource.
     * If it doesn't exist< create new one.
     * @return singleton instance for DataSource
     */
    public static DataSource getInstance() {
        LOCK.lock();
        DataSource instance = INSTANCE;
        if (instance == null) {
            instance = new DataSource();
            INSTANCE = instance;
        }
        LOCK.unlock();
        return instance;
    }
    /**
     * Get connection to database.
     * @return Connection instance
     * @throws DbManagerException if impossible to open database.
     */
    public Connection getConnection() throws DbManagerException {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(true);
        } catch (Exception exp) {
            System.err.println(exp.getClass().getName() + ": "
                    + exp.getMessage());
            throw new DbManagerException(EXCEPTION_MESSAGE + exp.getMessage());
        }
        System.out.println("Opened database successfully");
        return connection;
    }
}
