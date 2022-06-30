package by.khadasevich.accounting.controller.command;

/**
 * Interface Command for realisation command template.
 * All program functions will be released as separate
 * implementation of Command interface
 */
public interface Command {
    /**
     * Execute command.
     */
    void execute();
}
