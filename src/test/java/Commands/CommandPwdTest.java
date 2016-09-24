package Commands;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandPwdTest {
    CommandPwd commandPwd = new CommandPwd();

    @Test
    public void testGetName() {
        assertEquals("pwd", commandPwd.getName());
    }

    @Test
    public void testRun() {
        Feature feature = new Feature();
        feature = commandPwd.run(feature, null);
        assertEquals(1, feature.getResults().size());
        assertEquals(System.getProperty("user.dir"), feature.getResults().get(0));
        assertTrue(feature.getErrors().isEmpty());
    }
}