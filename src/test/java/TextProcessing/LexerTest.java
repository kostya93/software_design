package TextProcessing;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class LexerTest {
    Lexer lexer = new Lexer();

    @Test
    public void testRunSimple() throws TextProcessingError {
        String line = "str1 str2 str3";
        List<String> tokens = lexer.run(line);
        assertEquals(3, tokens.size());
        assertEquals("str1", tokens.get(0));
        assertEquals("str2", tokens.get(1));
        assertEquals("str3", tokens.get(2));
    }

    @Test
    public void testRunWithSingleQuotes() throws TextProcessingError {
        String line = "str1 'str2 str2' str3";
        List<String> tokens = lexer.run(line);
        assertEquals(3, tokens.size());
        assertEquals("str1", tokens.get(0));
        assertEquals("'str2 str2'", tokens.get(1));
        assertEquals("str3", tokens.get(2));
    }

    @Test
    public void testRunWithDoubleQuotes() throws TextProcessingError {
        String line = "str1 \"str2 str2\" str3";
        List<String> tokens = lexer.run(line);
        assertEquals(3, tokens.size());
        assertEquals("str1", tokens.get(0));
        assertEquals("\"str2 str2\"", tokens.get(1));
        assertEquals("str3", tokens.get(2));
    }

    @Test(expected = TextProcessingError.class)
    public void testRunWithWrongSingleQuotesBalance() throws TextProcessingError {
        String line = "str1 'str2 str3";
        lexer.run(line);
    }

    @Test(expected = TextProcessingError.class)
    public void testRunWithWrongDoubleQuotesBalance() throws TextProcessingError {
        String line = "str1 \"str2 str3";
        lexer.run(line);
    }
}