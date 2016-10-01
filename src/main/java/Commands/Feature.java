package Commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that stores the results of commands
 */
public class Feature {
    private List<String> errors;
    private List<String> results;
    private List<String> args;

    /**
     * @return agrs - arguments for command wich intended directly to this command
     * Example: echo "test_1.txt" "test_2.txt" | grep -A 5
     *  command echo returned feature, where
     *      results = ["test_1.txt", "test_2.txt"]
     *      args = ["-A", "5"]
     */
    public List<String> getArgs() {
        return args;
    }

    /**
     * @param args set args
     */
    public void setArgs(List<String> args) {
        this.args = args;
    }

    /**
     * create feature with empty results and errors
     */
    public Feature() {
        errors = new ArrayList<>();
        results = new ArrayList<>();
        args = new ArrayList<>();
    }

    /**
     * @return list of errors of all commands
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * @param error - add error to list if errors
     */
    public void addError(String error) {
        errors.add(error);
    }

    /**
     * @return results of previous command
     */
    public List<String> getResults() {
        return results;
    }

    /**
     * @param results - set result of current command
     */
    public void setResults(List<String> results) {
        this.results = results;
    }
}
