package Commands;

import java.util.*;

import static Commands.FileSystemUtility.getCurrentWorkingDirectory;

/**
 * Command pwd printing current dir
 */
public class CommandPwd implements Command {
    @Override
    public String getName() {
        return "pwd";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return current dir
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        feature.setResults(new ArrayList<>(Collections.singletonList(getCurrentWorkingDirectory())));
        return feature;
    }
}
