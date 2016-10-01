import Commands.*;
import TextProcessing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author kostya
 *
 * This class represents a program.
 * It contains the creation of entities required to work (like a commands, lexer, parser, ect.)
 * Also it contains the main loop, wich read user input, process it, and output a results.
 *
 */
public class Shell {
    public static void main(String[] args) throws IOException {
        Command[] commandObjects = {
                new CommandPwd(),
                new CommandExit(),
                new CommandEcho(),
                new CommandCat(),
                new CommandWc(),
                new CommandAssign()
        };

        Map<String, Command> availableCommands = new HashMap<>();
        Map<String, String> environment = new HashMap<>();

        for (Command commandObject: commandObjects) {
            availableCommands.put(commandObject.getName(), commandObject);
        }

        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Preprocessor preprocessor = new Preprocessor();

        ExternalCommandExecutor externalCommandExecutor = new ExternalCommandExecutor();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("shell: ");
        while ((line = bufferedReader.readLine()) != null) {
            List<String> tokens;
            List<CommandWithArgs> commandsWithArgs;
            try {
                tokens = lexer.run(line);
                tokens = preprocessor.run(tokens, environment);
                commandsWithArgs = parser.run(tokens);
            } catch (TextProcessingError e) {
                System.out.println(e);
                System.out.print("shell: ");
                continue;
            }

            Feature feature = new Feature();

            for (CommandWithArgs commandWithArgs: commandsWithArgs) {
                feature.setArgs(commandWithArgs.args);
                if (availableCommands.containsKey(commandWithArgs.command)) {
                    feature = availableCommands.get(commandWithArgs.command).run(feature, environment);
                } else {
                    feature = externalCommandExecutor.run(commandWithArgs.command, feature);
                }
            }

            for (String error : feature.getErrors()) {
                System.out.println("ERROR: " + error);
            }

            for (String res : feature.getResults()) {
                System.out.println(res);
            }

            System.out.print("shell: ");
        }
    }
}
