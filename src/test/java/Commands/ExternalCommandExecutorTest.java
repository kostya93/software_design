package Commands;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class ExternalCommandExecutorTest {
    ExternalCommandExecutor externalCommandExecutor = new ExternalCommandExecutor();

    @Test
    public void testRunWithWrongArgs() {
        Feature feature = new Feature();
        feature.setArgs(Arrays.asList("arg1", "arg2"));
        feature = externalCommandExecutor.run("wrong_command", feature);
        assertEquals(0, feature.getResults().size());
        assertEquals(1, feature.getErrors().size());
    }
}