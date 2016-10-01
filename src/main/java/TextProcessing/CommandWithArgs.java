package TextProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Class contains command and list of args for command
 */
public class CommandWithArgs {
    /**
     * name of command
     */
    public final String command;

    /**
     * list of args for command
     */
    public ArrayList<String> args;

    /**
     * @param command - command to create
     * @param args - list of args for creating command
     */
    CommandWithArgs(String command, ArrayList<String> args) {
        this.command = command;
        this.args = args;
    }
}
