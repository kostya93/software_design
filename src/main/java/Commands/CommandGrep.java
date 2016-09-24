package Commands;

import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Command to find pattern in text
 * Example:
 *  shell: echo "asd" "zxc" | grep "as"
 *  asd
 */
public class CommandGrep implements Command {
    @Override
    public String getName() {
        return "grep";
    }

    /**
     * find pattern in text
     * Options:
     *  -i - case case insensitive
     *  -w - only whole word matches
     *  -A n - print n lines after match
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        if (feature.getArgs().isEmpty()) {
            feature.addError("wrong num of args in command grep");
            feature.setResults(new ArrayList<>());
            return feature;
        }

        Options options = new Options();

        Option caseInsensitivity = new Option("i", "case_insensitivity", false, "case insensitivity");
        caseInsensitivity.setRequired(false);
        options.addOption(caseInsensitivity);

        Option wholeWord = new Option("w", "word", false, "find whole word");
        wholeWord.setRequired(false);
        options.addOption(wholeWord);

        Option wordAfter = new Option("A", "after", true, "print word after");
        wordAfter.setRequired(false);
        options.addOption(wordAfter);

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, feature.getArgs().toArray(new String[feature.getArgs().size()]));
        } catch (ParseException e) {
            feature.addError(e.getMessage());
            return feature;
        }

        boolean W = cmd.hasOption("w");
        int num = Integer.parseInt(cmd.getOptionValue("A", "-1"));

        List<String> result = new ArrayList<>();

        Pattern pattern;
        if (cmd.hasOption("i")) {
            pattern = Pattern.compile(feature.getArgs().get(0), Pattern.CASE_INSENSITIVE);
        } else  {
            pattern = Pattern.compile(feature.getArgs().get(0));
        }

        for (int i = 0; i < feature.getResults().size(); i++) {
            String curStr = feature.getResults().get(i);
            if(pattern.matcher(curStr).find() &&
                    (!cmd.hasOption("w") || checkWord(curStr, pattern))) {
                result.add(curStr);
                if (num >= 0) {
                    int last = Math.min(feature.getResults().size(), i + num + 1);
                    int j = i + 1;
                    while (j < last) {
                        result.add(feature.getResults().get(j));
                        j++;
                    }
                }
            }
        }

        feature.setResults(result);
        return feature;
    }

    private boolean checkWord(String line, Pattern pattern) {
        for(String str: line.split(" ")) {
            if(pattern.matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }
}
