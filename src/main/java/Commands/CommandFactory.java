package Commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for commands
 */
public class CommandFactory {
    private final static CommandFactory INSTANCE = new CommandFactory();
    private final Map<String, Command> availableCommands = new HashMap<>();

    private CommandFactory() {
        Command[] commandObjects = {
                new CommandPwd(),
                new CommandExit(),
                new CommandEcho(),
                new CommandCat(),
                new CommandWc(),
                new CommandAssign(),
                new CommandGrep()
        };

        for (Command commandObject: commandObjects) {
            availableCommands.put(commandObject.getName(), commandObject);
        }
    }

    /**
     * Return a Command by name or null if command does not exist.
     */
    public static Command getCommand(String commandName) {
        return INSTANCE.availableCommands.get(commandName);
    }
}
