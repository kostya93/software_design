package Commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import static org.junit.Assert.*;
/**
 * Created by kostya on 24.09.2016.
 */
public class CommandExitTest {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    CommandExit commandExit = new CommandExit();

    @Test
    public void testGetName() {
        assertEquals("exit", commandExit.getName());
    }

    @Test
    public void testRun() {
        exit.expectSystemExitWithStatus(0);
        commandExit.run(null, null);
        fail();
    }
}