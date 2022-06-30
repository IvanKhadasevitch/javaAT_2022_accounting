package by.khadasevich.accounting.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    /**
     * Max possible User's name length.
     */
    public static final int MAX_USER_NAME_LENGTH = 50;
    /**
     * Unique user indicator in database.
     */
    private int id;
    /**
     * User name.
     */
    private String name;
    /**
     * User address.
     */
    private String address;
    /**
     * Set [name] value.
     * it is no longer then MAX_USER_NAME_LENGTH
     * If [name] is longer then MAX_USER_NAME_LENGTH it is truncated.
     * @param userName is User's name to set
     */
    public void setName(final String userName) {
        if (userName.length() > MAX_USER_NAME_LENGTH) {
            this.name = userName.substring(0, MAX_USER_NAME_LENGTH);
        } else {
            this.name = userName;
        }
    }
}
