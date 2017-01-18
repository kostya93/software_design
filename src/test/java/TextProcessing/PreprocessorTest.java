package TextProcessing;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class PreprocessorTest {
    private Preprocessor preprocessor = Preprocessor.getInstance();

    @Test
    public void testRunSimple() {
        List<String> oldTokens = Arrays.asList("str1  str2", "str1 str2");
        List<String> newTokens = preprocessor.run(oldTokens, null);
        assertEquals(oldTokens, newTokens);
    }

    @Test
    public void testRunWithVariables() {
        List<String> oldTokens = Collections.singletonList("$var");
        Map<String, String> env = new HashMap<>();
        env.put("var", "valueOfVar");
        List<String> newTokens = preprocessor.run(oldTokens, env);
        assertEquals(Collections.singletonList("valueOfVar"), newTokens);
    }

    @Test
    public void testRunWithVariablesInDoubleQuotes() {
        List<String> oldTokens = Collections.singletonList("\"str $var str\"");
        Map<String, String> env = new HashMap<>();
        env.put("var", "valueOfVar");
        List<String> newTokens = preprocessor.run(oldTokens, env);
        assertEquals(Collections.singletonList("str valueOfVar str"), newTokens);
    }

    @Test
    public void testRunWithVariablesInSingleQuotes() {
        List<String> oldTokens = Collections.singletonList("'str $var str'");
        Map<String, String> env = new HashMap<>();
        env.put("var", "valueOfVar");
        List<String> newTokens = preprocessor.run(oldTokens, env);
        assertEquals(Collections.singletonList("str $var str"), newTokens);
    }
}