import Commands.Command;
import Commands.CommandFactory;
import Commands.ExternalCommandExecutor;
import Commands.Feature;
import TextProcessing.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kostya
 */

public class Shell {
    public static void main(String[] args) throws IOException {
        Map<String, String> environment = new HashMap<>();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        System.out.print("shell: ");
        while ((line = bufferedReader.readLine()) != null) {
            Feature feature = processInput(environment, line);
            if (feature != null) {
                feature.getErrors().forEach(error -> System.out.println("ERROR: " + error));
                feature.getResults().forEach(System.out::println);
            }
            System.out.print("shell: ");
        }
    }

    private static Feature processInput(Map<String, String> environment, String line) {
        Lexer lexer = Lexer.getInstance();
        Parser parser = Parser.getInstance();
        Preprocessor preprocessor = Preprocessor.getInstance();
        ExternalCommandExecutor externalCommandExecutor = ExternalCommandExecutor.getInstance();

        List<String> tokens;
        List<CommandWithArgs> commandsWithArgs;
        try {
            tokens = lexer.run(line);
            tokens = preprocessor.run(tokens, environment);
            commandsWithArgs = parser.run(tokens);
        } catch (TextProcessingError e) {
            System.out.println(e);
            return null;
        }

        Feature feature = new Feature();

        for (CommandWithArgs commandWithArgs: commandsWithArgs) {
            feature.setArgs(commandWithArgs.args);
            Command command = CommandFactory.getCommand(commandWithArgs.command);
            if (command != null) {
                feature = command.run(feature, environment);
            } else {
                feature = externalCommandExecutor.run(commandWithArgs.command, feature);
            }
        }

        return feature;
    }
}
