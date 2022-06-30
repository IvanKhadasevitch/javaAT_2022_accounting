package by.khadasevich.accounting.dal.db;

/**
 * Exception to indicate impossibility connection with database.
 *
 */
public class DbManagerException extends RuntimeException {
    public DbManagerException(final String message) {
        super(message);
    }
}
