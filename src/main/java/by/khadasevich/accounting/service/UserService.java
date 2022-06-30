package by.khadasevich.accounting.service;

import by.khadasevich.accounting.entities.User;

public interface UserService {
    /**
     * Create new User and save it in database.
     * @param name - User name
     * @param address - User address
     * @return saved in database User with id
     * or null if impossible to create or save User.
     */
    User addUser(String name, String address);
}
