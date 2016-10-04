package Commands;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import static Commands.FileSystemUtility.getCurrentWorkingDirectory;

/**
 * Command ls printing list of files (include dirs) for current directory
 */
public class CommandLs implements Command {
    @Override
    public String getName() {
        return "ls";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return list of files (include dirs) for current directory
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        File dir = new File(getCurrentWorkingDirectory());
        List<String> results = Arrays.asList(dir.listFiles()).stream().map(File::getName).collect(Collectors.toList());
        feature.setResults(results);
        return feature;
    }

}
