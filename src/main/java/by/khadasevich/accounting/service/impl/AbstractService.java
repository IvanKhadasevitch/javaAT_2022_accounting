package by.khadasevich.accounting.service.impl;

import by.khadasevich.accounting.dal.db.ConnectionManager;
import by.khadasevich.accounting.service.ServiceException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractService {

    public void startTransaction() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    public void stopTransaction() throws SQLException {
        ConnectionManager.getConnection().setAutoCommit(true);
    }

    public void commit() throws SQLException {
        ConnectionManager.getConnection().commit();
    }

    public void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new ServiceException("rollback error");
        }
    }
    private Connection getConnection() {
        return ConnectionManager.getConnection();
    }
}
