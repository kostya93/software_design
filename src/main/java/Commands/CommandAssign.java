package Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Command assign adds pair (key, value) to environment
 */
public class CommandAssign implements Command {
    @Override
    public String getName() {
        return "assign";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return feature with empty list of results
     * This method adds pair (key, value) to environment
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        if (feature.getArgs().size() != 2) {
            feature.addError("Wrong number of args");
            feature.setResults(new ArrayList<>());
            return feature;
        }
        env.put(feature.getArgs().get(0), feature.getArgs().get(1));
        feature.setResults(new ArrayList<>());
        return feature;
    }
}
