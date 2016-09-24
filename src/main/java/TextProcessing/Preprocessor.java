package TextProcessing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Preprocessor performs variable substitution from the environment
 * and transform assignment from "key=value" to "assign key value"
 */
public class Preprocessor {
    private class Variable {
        public int firstChar;
        public int lastChar;
        Variable (int firstChar, int lastChar) {
            this.firstChar = firstChar;
            this.lastChar = lastChar;
        }
    }

    /**
     * @param tokens - list of tokens
     * @param env - global environment
     * @return list of tokens that are variable substituted from the environment,
     * and transformed assignment from "key=value" to "assign key value"
     */
    public List<String> run(List<String> tokens, Map<String, String> env) {
        List<String> result = new ArrayList<>();
        for (String token: tokens) {
            if (token.charAt(0) != '\'') {
                int first = token.charAt(0) == '\"'? 1 : 0;
                int last = token.charAt(token.length() - 1) == '\"'? token.length() - 1 : token.length();
                List<Variable> variables = new ArrayList<>();
                int firstCharOfVariable = -1;
                for (int i = first; i < last; i++) {
                    switch (token.charAt(i)) {
                        case '$':
                            if (firstCharOfVariable >= 0) {
                                variables.add(new Variable(firstCharOfVariable, i));
                            } else {
                                firstCharOfVariable = i;
                            }
                            break;
                        case ' ':
                            if (firstCharOfVariable >= 0) {
                                variables.add(new Variable(firstCharOfVariable, i));
                                firstCharOfVariable = -1;
                            }
                            break;
                    }
                }
                if (firstCharOfVariable >= 0) {
                    variables.add(new Variable(firstCharOfVariable, last));
                }
                if (variables.isEmpty()) {
                    result.add(token.substring(first, last));
                } else {
                    StringBuilder builder = new StringBuilder();
                    int curFirst = first;
                    for (Variable var: variables) {
                        builder.append(token.substring(curFirst, var.firstChar))
                                .append(env.getOrDefault(token.substring(var.firstChar + 1, var.lastChar), ""))
                                .append(" ");
                        curFirst = var.lastChar + 1;
                    }
                    if (curFirst < last) {
                        builder.append(token.substring(curFirst, last));
                    } else {
                        builder.deleteCharAt(builder.length() - 1);
                    }
                    result.add(builder.toString());
                }
            } else {
                result.add(token.substring(1, token.length() - 1));
            }
        }
        if (result.size() == 1 && result.get(0).split("=").length == 2) {
            ArrayList<String> newResult = new ArrayList<>();
            newResult.add("assign");
            newResult.addAll(Arrays.asList(result.get(0).split("=")));
            result = newResult;
        }
        return result;
    }
}
