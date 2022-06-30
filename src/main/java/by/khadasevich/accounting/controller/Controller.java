package by.khadasevich.accounting.controller;

import by.khadasevich.accounting.controller.command.Command;
import by.khadasevich.accounting.controller.command.CommandName;
import by.khadasevich.accounting.view.TopMenu;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Controls logic of execution commands.
 */
public class Controller {
    /**
     * Execute task chosen in TopMenu.
     */
    public void executeCommand() {
        System.setOut(new PrintStream(System.out, true,
                StandardCharsets.UTF_8));
        System.setErr(new PrintStream(System.err, true,
                StandardCharsets.UTF_8));
        Command command;
        boolean continueWork = true;
        do {
            try {
                CommandName commandName = TopMenu.getMenuItem();
                command = commandName.getCommand();
                if (command != null) {
                    command.execute();
                } else {
                    continueWork = false;
                }
            } catch (Exception exp) {
                System.err.println(exp.getMessage());
            }
        } while (continueWork);
    }
}
