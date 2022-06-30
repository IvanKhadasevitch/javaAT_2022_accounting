package by.khadasevich.accounting.service.impl;

import by.khadasevich.accounting.dal.dao.UserDao;
import by.khadasevich.accounting.entities.User;
import by.khadasevich.accounting.service.ServiceException;
import by.khadasevich.accounting.service.UserService;
import by.khadasevich.accounting.util.SingletonBuilder;

import java.sql.SQLException;

public class UserServiceImpl extends AbstractService implements UserService {
    /**
     * Singleton Instance of UserDao.
     */
    private final UserDao userDao =
            SingletonBuilder.getInstanceImpl(UserDao.class);
    /**
     * Create new User and save it in database.
     *
     * @param name    - User name
     * @param address - User address
     * @return saved in database User with id
     * or null if impossible to create or save User.
     */
    @Override
    public User addUser(final String name, final String address) {
        if (name != null && address != null) {
            User user = new User();
            try {
                this.startTransaction();
                // fill parameters for new account
                user.setName(name);
                user.setAddress(address);
                // save User in database
                user = userDao.save(user);
                this.commit();
                this.stopTransaction();
                // return saved User
                return user;
            } catch (SQLException exp) {
                this.rollback();
                String errorMessage = "Error saving User: " + user;
                System.err.println(errorMessage + exp.getMessage());
                throw new ServiceException(errorMessage);
            }
        } else {
            return null;
        }
    }
}
