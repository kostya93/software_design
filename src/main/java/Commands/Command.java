package Commands;

import java.util.Map;

/**
 * It is a interface of command,
 * if you want create new command
 * you must implement this interface
 */
public interface Command {
    /**
     * Name it's unique identificator of command
     */
    String getName();

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return Feature, with contains args for next command and
     * list of errors
     */
    Feature run(Feature feature, Map<String, String> env);
}
