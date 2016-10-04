package Commands;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import static Commands.FileSystemUtility.getCurrentWorkingDirectory;
import static Commands.FileSystemUtility.setCurrentWorkingDirectory;

/**
 * Command cd which changes current working directory
 */
public class CommandCd implements Command {
    @Override
    public String getName() {
        return "cd";
    }

    /**
     * Change current working directory to provided directory
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return path to new current working directory
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        if (feature.getArgs().isEmpty()) {
            feature.addError("not enough arguments");
            return feature;
        }
        String curDir = getCurrentWorkingDirectory();
        String newDir = Paths.get(curDir).resolve(feature.getArgs().get(0)).toString();
        setCurrentWorkingDirectory(newDir);
        feature.setResults(new ArrayList<>(Collections.singletonList(String.format("Current working directory: %s", newDir))));
        return feature;
    }
}
