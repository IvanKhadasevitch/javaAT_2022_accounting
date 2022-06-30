package by.khadasevich.accounting.dal.keybord;

import by.khadasevich.accounting.entities.User;

public interface MakeUserByKeyBord {
    /**
     * Make new User instance with fields entered by keyboard without id field.
     * @return user instance where id is 0.
     */
    User makeUser();
}
