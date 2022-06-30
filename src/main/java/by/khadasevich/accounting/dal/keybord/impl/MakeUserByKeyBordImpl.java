package by.khadasevich.accounting.dal.keybord.impl;

import by.khadasevich.accounting.dal.keybord.MakeUserByKeyBord;
import by.khadasevich.accounting.entities.User;

import java.util.Scanner;

public final class MakeUserByKeyBordImpl implements MakeUserByKeyBord {
    // to override default public constructor
    private MakeUserByKeyBordImpl() {
        // no realisation needed
    }
    /**
     * Make new User instance with fields entered by keyboard without id field.
     *
     * @return user instance where id is 0.
     */
    @Override
    public User makeUser() {
        User user = new User();
        user.setName(takeUserName());
        user.setAddress(takeUserAddress());
        return user;
    }
    /**
     * Take entered by keyboard User name.
     * @return user's name
     * it is not empty and no longer then MAX_USER_NAME_LENGTH
     */
    private String takeUserName() {
        // take user's name
        System.out.printf("Enter User name no longer %d chars: ",
                User.MAX_USER_NAME_LENGTH);
        boolean isStopRead = false;
        String userName = null;
        do {
            Scanner scanner = new Scanner(System.in);
            String enteredName = scanner.nextLine();
            if (isValidUserName(enteredName)) {
                isStopRead = true;
                if (enteredName.length() > User.MAX_USER_NAME_LENGTH) {
                    userName = enteredName.substring(0,
                            User.MAX_USER_NAME_LENGTH);
                } else  {
                    userName = enteredName;
                }
            }
        } while (!isStopRead);
        return userName;
    }
    private boolean isValidUserName(final String userName) {
        if (userName == null) {
            return false;
        } else {
            return !(userName.isEmpty());
        }
    }
    /**
     * Take entered by keyboard User address.
     * @return is User address. Possible to be empty.
     */
    private String takeUserAddress() {
        // take user address
        System.out.println("Enter User address (you can omit it): ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
