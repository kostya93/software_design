package Commands;

import java.util.List;
import java.util.Map;

/**
 * Command exit interrupt a process shell
 */
public class CommandExit implements Command {
    @Override
    public String getName() {
        return "exit";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return nothing, because this command interrupt a process shell
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        System.exit(0);
        return null;
    }
}
