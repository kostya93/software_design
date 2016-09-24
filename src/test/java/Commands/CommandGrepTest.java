package Commands;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by kostya on 24.09.2016.
 */
public class CommandGrepTest {
    CommandGrep commandGrep = new CommandGrep();

    @Test
    public void testGetName() throws Exception {
        assertEquals("grep", commandGrep.getName());
    }

    @Test
    public void testRunSimple() {
        Feature feature = new Feature();
        feature.setResults(Arrays.asList("asd", "zxc"));
        feature.setArgs(Collections.singletonList("sd"));
        feature = commandGrep.run(feature, null);
        assertEquals(Collections.singletonList("asd"), feature.getResults());
        assertEquals(0, feature.getErrors().size());
    }

    @Test
    public void testRunCaseInsensitive() {
        Feature feature = new Feature();
        feature.setResults(Arrays.asList("asd qwe", "zxc"));
        feature.setArgs(Arrays.asList("SD", "-i"));
        feature = commandGrep.run(feature, null);
        assertEquals(Collections.singletonList("asd qwe"), feature.getResults());
        assertEquals(0, feature.getErrors().size());
    }

    @Test
    public void testRunWholeWord() {
        Feature feature = new Feature();
        feature.setResults(Arrays.asList("asd qwe", "zxc rty", "zxc sd zxc"));
        feature.setArgs(Arrays.asList("sd", "-w"));
        feature = commandGrep.run(feature, null);
        assertEquals(Collections.singletonList("zxc sd zxc"), feature.getResults());
        assertEquals(0, feature.getErrors().size());
    }

    @Test
    public void testRunLinesAfterMatch() {
        Feature feature = new Feature();
        feature.setResults(Arrays.asList("str1", "str2", "str3", "str4", "str5"));
        feature.setArgs(Arrays.asList("str2", "-A", "2"));
        feature = commandGrep.run(feature, null);
        assertEquals(Arrays.asList("str2", "str3", "str4"), feature.getResults());
        assertEquals(0, feature.getErrors().size());
    }
}