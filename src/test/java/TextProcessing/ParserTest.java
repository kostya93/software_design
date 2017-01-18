package TextProcessing;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class ParserTest {
    Parser parser = Parser.getInstance();

    @Test
    public void testRunSimple() throws TextProcessingError {
        List<CommandWithArgs> commandsWithArgs = parser.run(Arrays.asList("command", "agr1", "arg2"));
        assertEquals(1, commandsWithArgs.size());
        assertEquals("command", commandsWithArgs.get(0).command);
        assertEquals(Arrays.asList("agr1", "arg2"), commandsWithArgs.get(0).args);
    }

    @Test
    public void testRunWithPipe() throws TextProcessingError {
        List<CommandWithArgs> commandsWithArgs = parser.run(Arrays.asList(
                "command1", "agr1_1", "arg1_2",
                "|",
                "command2", "agr2_1", "arg2_2",
                "|",
                "command3", "agr3_1", "arg3_2"
        ));
        assertEquals(3, commandsWithArgs.size());
        assertEquals("command1", commandsWithArgs.get(0).command);
        assertEquals("command2", commandsWithArgs.get(1).command);
        assertEquals("command3", commandsWithArgs.get(2).command);
        assertEquals(Arrays.asList("agr1_1", "arg1_2"), commandsWithArgs.get(0).args);
        assertEquals(Arrays.asList("agr2_1", "arg2_2"), commandsWithArgs.get(1).args);
        assertEquals(Arrays.asList("agr3_1", "arg3_2"), commandsWithArgs.get(2).args);
    }

    @Test(expected = TextProcessingError.class)
    public void testRunWithFirstPipe() throws TextProcessingError {
        parser.run(Arrays.asList(
                "|",
                "command1", "agr1_1", "arg1_2",
                "|",
                "command2", "agr2_1", "arg2_2",
                "|",
                "command3", "agr3_1", "arg3_2"
        ));
    }

    @Test(expected = TextProcessingError.class)
    public void testRunWithLastPipe() throws TextProcessingError {
        parser.run(Arrays.asList(
                "command1", "agr1_1", "arg1_2",
                "|",
                "command2", "agr2_1", "arg2_2",
                "|",
                "command3", "agr3_1", "arg3_2",
                "|"
        ));
    }

    @Test(expected = TextProcessingError.class)
    public void testRunWithDoublePipe() throws TextProcessingError {
        parser.run(Arrays.asList(
                "command1", "agr1_1", "arg1_2",
                "|",
                "command2", "agr2_1", "arg2_2",
                "|",
                "|",
                "command3", "agr3_1", "arg3_2"
        ));
    }
}