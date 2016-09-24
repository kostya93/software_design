package TextProcessing;

import java.util.ArrayList;
import java.util.List;

/**
 * Lexer split input text into tokens
 * Example:
 * Input: echo "asdas a, asdsda"  ' xv xcv"
 * Output [echo, "asdas a, asdsda", ' xv xcv"]
 */
public class Lexer {
    /**
     * @param line - input text of which will be split into tokens
     * @return list of tokens
     * @throws TextProcessingError
     */
    public List<String> run(String line) throws TextProcessingError {
        List<String> result = new ArrayList<>();
        boolean isDoubleQuotes = false;
        boolean isSingleQuotes = false;
        int firstCharOfCurrentToken = -1;
        boolean isSpace = true;
        for (int i = 0; i < line.length(); i++) {
            switch (line.charAt(i)) {
                case '\"':
                    if (isSingleQuotes) {
                        break;
                    }
                    if (isDoubleQuotes) {
                        if (i == line.length() - 1 || line.charAt(i + 1) == ' ') {
                            isDoubleQuotes = false;
                        } else {
                            throw new TextProcessingError("lexer error at symbol " + i);
                        }
                    } else {
                        if (isSpace) {
                            firstCharOfCurrentToken = i;
                            isDoubleQuotes = true;
                            isSpace = false;
                        } else {
                            throw new TextProcessingError("lexer error at symbol " + i);
                        }
                    }
                    break;
                case '\'':
                    if (isDoubleQuotes) {
                        break;
                    }
                    if (isSingleQuotes) {
                        if (i == line.length() - 1 || line.charAt(i + 1) == ' ') {
                            isSingleQuotes = false;
                        } else {
                            throw new TextProcessingError("lexer error at symbol " + i);
                        }
                    } else {
                        if (isSpace) {
                            isSingleQuotes = true;
                            firstCharOfCurrentToken = i;
                            isSpace = false;
                        } else {
                            throw new TextProcessingError("lexer error at symbol " + i);
                        }
                    }
                    break;
                case ' ':
                    if (isSpace || isDoubleQuotes || isSingleQuotes) {
                        break;
                    }
                    result.add(line.substring(firstCharOfCurrentToken, i));
                    isSpace = true;
                    break;
                default:
                    if (isSpace) {
                        firstCharOfCurrentToken = i;
                        isSpace = false;
                    }
                    break;
            }
        }
        if (isSingleQuotes || isDoubleQuotes) {
            throw new TextProcessingError("lexer error with quotes");
        }
        if (firstCharOfCurrentToken >= 0) {
            result.add(line.substring(firstCharOfCurrentToken));
        }

        return result;
    }
}
