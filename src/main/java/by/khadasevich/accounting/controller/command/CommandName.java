package by.khadasevich.accounting.controller.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Describe all possible function of program.
 * It Defines displayed name of command for user,
 * and it's realisation.
 */
@Getter
@AllArgsConstructor
public enum CommandName {
    /**
     * To register new user and save new one at database.
     */
    USER_REGISTRATION("User registration.", new SaveUserCommand()),
    /**
     * Assign account to user. Account is saved in database.
     */
    ADD_ACCOUNT_TO_USER("Add account to user.", new AddAccountToUserCommand()),
    /**
     * Depositing funds into user account. Transaction is saved in database.
     */
    DEPOSITING_FUNDS("Depositing funds into an account.",
            new DepositingFundsCommand()),
    /**
     * Withdrawal of funds from the account. Transaction is saved in database.
     */
    WITHDRAWAL_FUNDS("Withdrawal of funds from the account.",
            new WithdrawalFundsCommand()),
    /**
     * Exit of programme working.
     */
    EXIT("Exit", null);
    /**
     * Command title. It will be read by the user.
     */
    private final String title;
    /**
     * Implementation of Command.
     */
    private final Command command;
}
