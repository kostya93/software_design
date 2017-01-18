package TextProcessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parser transform list of tokens to list of commands with args
 * Example:
 *  Input: [echo, arg1, arg2, |, cat file]
 *  Output:
 *      [
 *        {
 *          command: echo,
 *          args: [arg1, arg2]
 *        },
 *        {
 *          command: cat,
 *          args: [file]
 *        }
 *      ]
 */
public class Parser {
    private static final Parser INSTANCE = new Parser();
    private Parser() {}

    /**
     * @param tokens
     * @return list of commands with args
     * @throws TextProcessingError
     */
    public List<CommandWithArgs> run(List<String> tokens) throws TextProcessingError {
        if (tokens.isEmpty()) {
            return Collections.emptyList();
        }
        String currentCommand = tokens.get(0);
        if (currentCommand.equals("|")) {
            throw new TextProcessingError("parser error at token 0");
        }
        List<CommandWithArgs> result = new ArrayList<>();
        result.add(new CommandWithArgs(currentCommand, new ArrayList<>()));
        for (int i = 1; i < tokens.size(); i++) {
            String s = tokens.get(i);
            if (s.equals("|")) {
                i++;
                if (i == tokens.size()) {
                    throw new TextProcessingError("parser error at token " + (i - 1));
                }
                currentCommand = tokens.get(i);
                if (currentCommand.equals("|")) {
                    throw new TextProcessingError("parser error at token " + i);
                }
                result.add(new CommandWithArgs(currentCommand, new ArrayList<>()));
            } else {
                result.get(result.size() - 1).args.add(s);
            }
        }
        return result;
    }

    public static Parser getInstance() {
        return INSTANCE;
    }
}
