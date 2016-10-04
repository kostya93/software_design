package Commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Stream;

import static Commands.FileSystemUtility.resolveFilePath;

/**
 * Command wc counts the number of words, lines, chars of input file
 * Example:
 *  test.txt:
 *      some line 1
 *      some line 2
 *
 *  shell: wc file.txt
 *  6 2 22 file.txt
 */
public class CommandWc implements Command {
    @Override
    public String getName() {
        return "wc";
    }

    /**
     * @param feature contains results of previous command in feature.getResults()
     * @param env its global environment
     * @return words, lines, chars of input file
     */
    @Override
    public Feature run(Feature feature, Map<String, String> env) {
        ArrayList<String> result = new ArrayList<>();
        final int[] words = {0};
        final int[] lines = {0};
        final int[] chars = {0};
        Stream.concat(feature.getArgs().stream(), feature.getResults().stream()).forEach(file -> {
            Path filePath = resolveFilePath(file);
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines[0]++;
                    words[0] += line.split(" ").length;
                    chars[0] += line.length();
                }
                result.add(String.format("%d %d %d %s", words[0], lines[0], chars[0], file));
            } catch (IOException x) {
                feature.addError("Wrong file: " + filePath.toString());
            }
        });
        feature.setResults(result);
        return feature;
    }
}
