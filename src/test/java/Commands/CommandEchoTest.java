package Commands;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandEchoTest {
    CommandEcho commandEcho = new CommandEcho();

    @Test
    public void testGetName() {
        assertEquals("echo", commandEcho.getName());
    }

    @Test
    public void testRun() {
        List<String> actual = Arrays.asList("some line 1", "some line 2");
        List<String> expected = new ArrayList<>(actual);

        Feature feature = new Feature();
        feature.setArgs(actual);
        feature = commandEcho.run(feature, null);
        assertEquals(expected, feature.getResults());
    }
}