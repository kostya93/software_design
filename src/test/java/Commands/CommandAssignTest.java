package Commands;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandAssignTest {
    CommandAssign commandAssign = new CommandAssign();

    @Test
    public void testGetName() {
        assertEquals(commandAssign.getName(), "assign");
    }

    @Test
    public void testRunWithWrongArgs() {
        Map<String, String> env = new HashMap<>();
        env.put("someKey", "someValue");

        Map<String, String> oldEnv = new HashMap<>(env);

        Feature feature = new Feature();
        feature.setArgs(Arrays.asList("asd", "zxc", "qwe"));

        feature = commandAssign.run(feature, env);

        assertEquals(oldEnv, env);
        assertTrue(feature.getResults().isEmpty());
        assertEquals(1, feature.getErrors().size());
    }

    @Test
    public void testRunWithCorrectArgs() {
        Map<String, String> env = new HashMap<>();
        env.put("someKey", "someValue");

        Map<String, String> correctEnv = new HashMap<>(env);
        final String key = "someKey2";
        final String value = "someValue2";
        correctEnv.put(key, value);

        Feature feature = new Feature();
        feature.setArgs(Arrays.asList(key, value));

        feature = commandAssign.run(feature, env);

        assertEquals(correctEnv, env);
        assertTrue(feature.getResults().isEmpty());
    }
}