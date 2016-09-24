package Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Command cat print content of file
 */
public class CommandCat implements Command {
    @Override
    public String getName() {
        return "cat";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return feature with list of lines of file
     *
     * Example:
     *  cat filename
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        ArrayList<String> result = new ArrayList<>();
        Stream.concat(feature.getArgs().stream(), feature.getResults().stream()).forEach(file -> {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    result.add(line);
                }
            } catch (IOException x) {
                feature.addError("Wrong file" + file);
            }
        });
        feature.setResults(result);
        return feature;
    }
}
