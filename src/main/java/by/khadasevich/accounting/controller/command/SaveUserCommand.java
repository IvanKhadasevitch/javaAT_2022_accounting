package by.khadasevich.accounting.controller.command;

import by.khadasevich.accounting.dal.db.ConnectionManager;
import by.khadasevich.accounting.dal.keybord.MakeUserByKeyBord;
import by.khadasevich.accounting.entities.User;
import by.khadasevich.accounting.service.UserService;
import by.khadasevich.accounting.util.SingletonBuilder;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Implementation Command that save new user in database.
 */
public class SaveUserCommand implements Command {
    /**
     * Execute command that save new user in database.
     */
    @Override
    public void execute() {
        // form new user for save
        User user = SingletonBuilder.getInstanceImpl(MakeUserByKeyBord.class)
                .makeUser();
        System.out.println("User before save: " + user);
        // save user in database
        UserService userService =
                SingletonBuilder.getInstanceImpl(UserService.class);
        user = userService.addUser(user.getName(), user.getAddress());
        System.out.println("User after save: " + user);
        // close connection to database
        ConnectionManager.closeConnection();
    }
}
