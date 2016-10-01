package Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Command echo printing its args and result of prev. command
 */
public class CommandEcho implements Command {
    @Override
    public String getName() {
        return "echo";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return concatenated args and results
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        List<String> result = new ArrayList<>();
        result.addAll(feature.getArgs());
        result.addAll(feature.getResults());
        feature.setResults(result);
        return feature;
    }
}
