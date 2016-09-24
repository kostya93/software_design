package Commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * ExternalCommandExecutor execute external command
 */
public class ExternalCommandExecutor {
    /**
     * @param command - external command to execute
     * @param feature - params to external command
     * @return result of external command
     */
    public Feature run(String command, Feature feature) {
        command += String.join(" ", feature.getArgs()) + String.join(" ", feature.getResults());
        ArrayList<String> result = new ArrayList<>();
        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line;
            while ((line = reader.readLine())!= null) {
                result.add(line);
            }
        } catch (Exception e) {
            feature.addError("wrong external command \"" + command + "\"");
        }
        feature.setResults(result);
        return feature;
    }
}
